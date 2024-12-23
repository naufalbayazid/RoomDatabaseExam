package com.example.roomdatabaseexam.ui.VmMatkul

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseexam.Data.repository.RepositoryDosen
import com.example.roomdatabaseexam.Data.repository.RepositoryMatkul
import com.example.roomdatabaseexam.Entity.Matkul
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMatkulViewModel(
    private val repositoryMatkul: RepositoryMatkul,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    val homeMkUIState: StateFlow<HomeMkUiState> = repositoryMatkul.getAllMatkul()
        .filterNotNull()
        .map {
            HomeMkUiState(
                listMk = it.toList(),
                isloading = false
            )
        }
        .onStart {
            emit(HomeMkUiState(isloading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeMkUiState(
                    isloading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMkUiState(
                isloading = true
            )
        )

}

data class HomeMkUiState(
    val listMk: List<Matkul> = listOf(),
    val isloading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)