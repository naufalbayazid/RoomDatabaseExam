package com.example.roomdatabaseexam.Data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.roomdatabaseexam.Entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    @Query("SELECT * FROM Dosen order by nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>

}