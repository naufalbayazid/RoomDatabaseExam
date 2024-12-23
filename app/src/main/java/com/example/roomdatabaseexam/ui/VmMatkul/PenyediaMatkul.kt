package com.example.roomdatabaseexam.ui.VmMatkul

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roomdatabaseexam.KrsApp

object PenyediaMatkul {
    val Factory = viewModelFactory {

        initializer {
            MatkulViewModel(
                krsApp().containerApp.repositoryMatkul,
                krsApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeMatkulViewModel(
                krsApp().containerApp.repositoryMatkul,
                krsApp().containerApp.repositoryDosen
            )
        }
        initializer {
            DetailMatkulViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatkul
            )
        }
        initializer {
            UpdateMatkulViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatkul,
                krsApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)