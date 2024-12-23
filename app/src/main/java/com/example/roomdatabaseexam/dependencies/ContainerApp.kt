package com.example.roomdatabaseexam.dependencies

import android.content.Context
import com.example.roomdatabaseexam.Data.Database.KrsDatabase
import com.example.roomdatabaseexam.Data.repository.LocalRepoDosen
import com.example.roomdatabaseexam.Data.repository.LocalRepoMatkul
import com.example.roomdatabaseexam.Data.repository.RepositoryDosen
import com.example.roomdatabaseexam.Data.repository.RepositoryMatkul

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMatkul: RepositoryMatkul
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepoDosen(KrsDatabase.getDatabase(context).dosenDao())
    }
    override val repositoryMatkul: RepositoryMatkul by lazy {
        val database = KrsDatabase.getDatabase(context)
        LocalRepoMatkul(
            matkulDao = database.matkulDao(),
            dosenDao = database.dosenDao() // Tambahkan dosenDao di sini
        )
    }

}