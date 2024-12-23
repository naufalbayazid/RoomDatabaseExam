package com.example.roomdatabaseexam.Data.repository

import com.example.roomdatabaseexam.Data.dao.DosenDao
import com.example.roomdatabaseexam.Entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepoDosen (private val dosenDao: DosenDao
) : RepositoryDosen {
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }
}