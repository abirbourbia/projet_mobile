package com.example.carenta

data class Car(var image: String,
               var model:String,
               var motor: String,
               var tarif: String,
               var disponibility:String,
               var marque: String,
               val x: String,
               val y: String,
               val id_car: Int)
    : java.io.Serializable