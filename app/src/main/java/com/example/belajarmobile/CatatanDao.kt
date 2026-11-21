package com.example.belajarmobile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatatanDao {
    @Insert
    suspend fun tambah(catatan: Catatan)

    @Query("SELECT * FROM catatan ORDER BY id DESC")
    suspend fun semua(): List<Catatan>
}