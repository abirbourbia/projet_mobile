package com.example.carenta
import java.io.Serializable

data class reservation(
    val id:Int ?= null,
    val id_car:Int ?=null,
    val id_user:Int ?=null,
    val dateDebut: String,
    val dateFin: String,
    val destination: String,
    val source: String,
    val pincode: String
) : Serializable