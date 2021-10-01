package com.example.almuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etNama : EditText
    private lateinit var etAlmat : EditText
    private lateinit var etCodi : EditText
    private lateinit var btnSave : Button
    private lateinit var listmhs : ListView
    private lateinit var ref : DatabaseReference
    private lateinit var mhsList: MutableList<Alumno>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("alumno")

        etCodi = findViewById(R.id.et_codi)
        etNama = findViewById(R.id.et_nama)
        etAlmat = findViewById(R.id.et_almat)
        btnSave = findViewById(R.id.btn_save)
        listmhs = findViewById(R.id.lv_mhs)
        btnSave.setOnClickListener(this)

        mhsList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not implemented")

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (h in p0.children){
                        val alumno = h.getValue(Alumno::class.java)
                        if (alumno != null) {
                            mhsList.add(alumno)
                        }
                    }

                    val adapter = ListaAdap(this@MainActivity, R.layout.item_mhs, mhsList)
                    listmhs.adapter = adapter
                }

            }


        })

    }




    override fun onClick(p0: View?) {
        saveData()
    }

    private fun saveData(){
        val codi = etCodi.text.toString().trim()
        val nama = etNama.text.toString().trim()
        val alamat = etAlmat.text.toString().trim()


        if (codi.isEmpty()){
            etCodi.error = "Error Codigo"
            return
        }

        if (nama.isEmpty()){
            etNama.error = "Error Nombre!"
            return
        }

        if (alamat.isEmpty()){
            etAlmat.error = "Error Apellido"
            return
        }


        val alumId = ref.push().key

        val alum = Alumno(alumId,codi, nama,alamat)

        if (alumId != null) {
            ref.child(alumId).setValue(alum).addOnCompleteListener{
                Toast.makeText(applicationContext, "data jaime", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
