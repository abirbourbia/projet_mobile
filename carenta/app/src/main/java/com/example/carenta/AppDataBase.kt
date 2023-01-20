package com.example.carenta

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [Trajet::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun getTrajetDao():TrajetDao
}