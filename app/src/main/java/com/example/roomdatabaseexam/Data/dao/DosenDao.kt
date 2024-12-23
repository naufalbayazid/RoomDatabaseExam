package com.example.roomdatabaseexam.Data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.roomdatabaseexam.Entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

@Query("SELECT * FROM Dosen order by nama ASC")
fun getAllDosen(): Flow<Dosen>

@Query("SELECT * FROM  Dosen WHERE nidn = :nidn")
fun getDosen(nidn:String): Flow<Dosen>

@Delete
suspend fun deleteDosen(dosen: Dosen)

@Update
fun updateDosen(dosen: Dosen)

}