package com.example.almuno
data class Alumno(

    val id : String?,
    val codi :  String,
    val nama: String,
    val alamat : String


){
    constructor() : this("","","","" ){

    }
}