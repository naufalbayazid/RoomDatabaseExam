package com.example.roomdatabaseexam.Data.repository

import com.example.roomdatabaseexam.Entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {
    suspend fun insertDosen(dosen: Dosen)

    fun getAllDosen(): Flow<List<Dosen>>

}