package com.example.belajarmobile

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Catatan::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catatanDao(): CatatanDao
}