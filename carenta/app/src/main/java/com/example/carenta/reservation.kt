package com.example.carenta

data class reservation(
    val id:Int ?= null,
    val id_car:Int ?=null,
    val id_user:Int ?=null,
    val dateDebut: String,
    val dateFin: String,
    val destination: String,
    val source: String,
) : java.io.Serializable