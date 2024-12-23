package com.example.roomdatabaseexam.ui.VMDosen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseexam.Data.repository.RepositoryDosen
import com.example.roomdatabaseexam.Entity.Dosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDosenviewModel(
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    val homeUIState: StateFlow<HomeUiState> = repositoryDosen.getAllDosen()
        .filterNotNull()
        .map {
            HomeUiState(
                listDsn = it.toList(),
                isloading = false
            )
        }
        .onStart {
            emit(HomeUiState(isloading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isloading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                isloading = true
            )
        )

}

data class HomeUiState(
    val listDsn: List<Dosen> = listOf(),
    val isloading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)