package com.example.roomdatabaseexam.ui.VmMatkul

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseexam.Data.repository.RepositoryDosen
import com.example.roomdatabaseexam.Data.repository.RepositoryMatkul
import com.example.roomdatabaseexam.Entity.Dosen
import com.example.roomdatabaseexam.Entity.Matkul
import com.example.roomdatabaseexam.ui.Navigasi.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatkulViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatkul: RepositoryMatkul,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    var updateUIState by mutableStateOf(MkUIState())
        private set

    var dosenList by mutableStateOf(emptyList<Dosen>())
        private set

    private val _kdMk: String = checkNotNull(savedStateHandle[DestinasiUpdate.KDMK])

    // Fetch matkul data and dosen list on initialization
    init {
        viewModelScope.launch {
            // Fetch matkul
            updateUIState = repositoryMatkul.getMatkul(_kdMk)
                .filterNotNull()
                .first()
                .toUIstateMk()

            // Fetch dosen list
            repositoryDosen.getAllDosen().collect { dosenEntities ->
                dosenList = dosenEntities
            }
        }
    }

    // Update state with matkul event
    fun updateState(matkulEvent: MatkulEvent) {
        updateUIState = updateUIState.copy(
            matkulEvent = matkulEvent,
        )
    }

    // Validate form fields
    fun validateFields(): Boolean {
        val event = updateUIState.matkulEvent
        val errorState = FormErrorStateMk(
            kdMk = if (event.kdMk.isNotEmpty()) null else "Kode tidak boleh kosong",
            namaMk = if (event.namaMk.isNotEmpty()) null else "Matakuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            smstr = if (event.smstr.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Matakuliah tidak boleh kosong",
            dospem = if (event.dospem.isNotEmpty()) null else "Pengampu tidak boleh kosong",
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Update data in repository
    fun updateData() {
        val currentEvent = updateUIState.matkulEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatkul.updateMatkul(currentEvent.toMatkulEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        matkulEvent = MatkulEvent(),
                        isEntryValid = FormErrorStateMk()
                    )
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate: ${e.message}"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate: Validasi gagal"
            )
        }
    }

    // Reset snackBarMessage
    fun resetsnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Matkul.toUIstateMk(): MkUIState = MkUIState(
    matkulEvent = this.toDetailUiEvent(),
)