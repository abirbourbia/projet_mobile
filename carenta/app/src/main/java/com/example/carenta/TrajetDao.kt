package com.example.carenta

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrajetDao {
    @Insert
    fun addTrajet(trajet:Trajet)

    @Query("select * from trajets")
    fun getTrajets():List<Trajet>

}