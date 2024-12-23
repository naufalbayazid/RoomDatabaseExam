package com.example.roomdatabaseexam.Data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomdatabaseexam.Entity.Matkul
import kotlinx.coroutines.flow.Flow

@Dao
interface MatkulDao {
    @Insert
    suspend fun insertMatkul(Matkul : Matkul)

    @Query("SELECT * FROM Matkul order by namaMk  ASC")
    fun getAllMatkul(): Flow<List<Matkul>>

    @Query("SELECT * FROM Matkul WHERE kdMk =  :kdMk")
    fun getMatkul(kdMk:String) :Flow<Matkul>

    @Delete
    suspend fun deleteMatkul(matkul: Matkul)

    @Update
    suspend fun updateMatkul(matkul: Matkul)

}