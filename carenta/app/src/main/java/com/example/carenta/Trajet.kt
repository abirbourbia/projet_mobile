package com.example.carenta

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trajets")
data class Trajet(
    @PrimaryKey (autoGenerate = true) // cle auto genere
    val id:Int ?= null,
    val date: String,
    val hour_debut: String,
    val hour_fin: String,
    val cout: Int,
)