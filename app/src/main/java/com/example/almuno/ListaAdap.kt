package com.example.almuno

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class ListaAdap(val lisTx : Context, val layoutResId : Int, val mhsList :List<Alumno>) : ArrayAdapter<Alumno> ( lisTx,layoutResId, mhsList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lisTx)

        val view: View = layoutInflater.inflate(layoutResId, null)

        val tvCodi: TextView = view.findViewById(R.id.tv_codi)
        val tvNama: TextView = view.findViewById(R.id.tv_nama)
        val tvAlamat: TextView = view.findViewById(R.id.tv_alamat)
        val tvEdit: TextView = view.findViewById(R.id.tv_edit)



        val Alumno = mhsList[position]

        tvEdit.setOnClickListener {
            showUpdateDialog(Alumno)
        }

        tvCodi.text = Alumno.codi
        tvNama.text = Alumno.nama
        tvAlamat.text = Alumno.alamat


        return view
    }

    fun showUpdateDialog(Alumno: Alumno) {
        val builder = AlertDialog.Builder(lisTx)
        builder.setTitle("Editar Datos")

        val inflater = LayoutInflater.from(lisTx)
        val view = inflater.inflate(R.layout.update_dialog, null)

        val etCodi = view.findViewById<EditText>(R.id.et_codi)
        val etNama = view.findViewById<EditText>(R.id.et_nama)
        val etAlamat = view.findViewById<EditText>(R.id.et_almat)

        etCodi.setText(Alumno.codi)
        etNama.setText(Alumno.nama)
        etAlamat.setText(Alumno.alamat)



        builder.setView(view)

        builder.setPositiveButton("Actualizar") { p0, p1 ->
            val dbMhs = FirebaseDatabase.getInstance().getReference("alumno")

            val codi =etCodi.text.toString().trim()
            val nama =etNama.text.toString().trim()
            val alamat =etAlamat.text.toString().trim()
            if(codi.isEmpty()){
                etCodi.error = "holahola"
                etCodi.requestFocus()
                return@setPositiveButton
            }
            if (nama.isEmpty()){
                etNama.error ="holaholaN"
                etCodi.requestFocus()
                return@setPositiveButton
            }
            if (alamat.isEmpty()){
                etAlamat.error = "holholaa"
                etAlamat.requestFocus()
                return@setPositiveButton
            }

            val Alumno = com.example.almuno.Alumno(Alumno.id, codi, nama, alamat)

            dbMhs.child(Alumno.id!!).setValue(Alumno)

            Toast.makeText(lisTx, "Actualizado", Toast.LENGTH_SHORT).show()

        }

        builder.setNegativeButton("No"){p0, p1 ->

        }

        val alert =builder.create()
        alert.show()
    }

}