package com.example.roomdatabaseexam.Data.repository

import com.example.roomdatabaseexam.Data.dao.DosenDao
import com.example.roomdatabaseexam.Data.dao.MatkulDao
import com.example.roomdatabaseexam.Entity.Dosen
import com.example.roomdatabaseexam.Entity.Matkul
import kotlinx.coroutines.flow.Flow

class LocalRepoMatkul (private val matkulDao: MatkulDao,
                       private val dosenDao: DosenDao // Tambahkan ini
) : RepositoryMatkul {
    override suspend fun insertMatkul(matkul: Matkul) {
        matkulDao.insertMatkul(matkul)
    }

    override fun getAllMatkul(): Flow<List<Matkul>> {
        return matkulDao.getAllMatkul()
    }

    override fun getMatkul(kdMk: String): Flow<Matkul> {
        return matkulDao.getMatkul(kdMk)
    }

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override suspend fun deleteMatkul(matkul: Matkul) {
        matkulDao.deleteMatkul(matkul)
    }

    override suspend fun updateMatkul(matkul: Matkul) {
        matkulDao.updateMatkul(matkul)
    }
}