package com.example.roomdatabaseexam.Data.repository

import com.example.roomdatabaseexam.Entity.Dosen
import com.example.roomdatabaseexam.Entity.Matkul
import kotlinx.coroutines.flow.Flow

interface RepositoryMatkul {
    suspend fun insertMatkul(matkul: Matkul)

    fun getAllMatkul(): Flow<List<Matkul>>

    fun getAllDosen(): Flow<List<Dosen>>

    fun getMatkul(kdMk: String): Flow<Matkul>

    suspend fun deleteMatkul(matkul: Matkul)

    suspend fun updateMatkul(matkul: Matkul)
}