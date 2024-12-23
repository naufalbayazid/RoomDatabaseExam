package com.example.roomdatabaseexam.ui.VmMatkul

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseexam.Data.repository.RepositoryDosen
import com.example.roomdatabaseexam.Data.repository.RepositoryMatkul
import com.example.roomdatabaseexam.Entity.Dosen
import com.example.roomdatabaseexam.Entity.Matkul
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MatkulViewModel(private val repositoryMatkul: RepositoryMatkul, private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var uiStateMk by mutableStateOf(MkUIState())

    var dosenList by mutableStateOf(listOf<String>())

    init {
        loadDosenList()
    }

    // Memperbarui state berdasarkan input pengguna
    fun updateStateMatkul(matkulEvent: MatkulEvent) {
        uiStateMk = uiStateMk.copy(
            matkulEvent = matkulEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = uiStateMk.matkulEvent
        val errorState = FormErrorStateMk(
            kdMk = if (event.kdMk.isNotEmpty()) null else "Kode tidak boleh kosong",
            namaMk = if (event.namaMk.isNotEmpty()) null else "Nama Matkul tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            smstr = if (event.smstr.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Matkul tidak boleh kosong",
            dospem = if (event.dospem.isNotEmpty()) null else "Dospem tidak boleh kosong",
        )
        uiStateMk = uiStateMk.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiStateMk.matkulEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatkul.insertMatkul(currentEvent.toMatkulEntity())
                    uiStateMk = uiStateMk.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        matkulEvent = MatkulEvent(), // Reset input form
                        isEntryValid = FormErrorStateMk() // Reset error state
                    )
                } catch (e: Exception) {
                    uiStateMk = uiStateMk.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateMk = uiStateMk.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        uiStateMk = uiStateMk.copy(snackBarMessage = null)
    }

    private fun loadDosenList() {
        viewModelScope.launch {
            repositoryMatkul.getAllDosen() // Menggunakan Flow untuk mendapatkan daftar dosen
                .onStart {
                    uiStateMk = uiStateMk.copy(isLoading = true)
                }
                .catch { e ->
                    uiStateMk = uiStateMk.copy(
                        snackBarMessage = "Gagal memuat daftar dosen: ${e.message}",
                        isLoading = false
                    )
                }
                .collect { dosenList ->
                    uiStateMk = uiStateMk.copy(
                        dosenList = dosenList,
                        isLoading = false
                    )
                }
        }
    }
}

data class MkUIState(
    val matkulEvent: MatkulEvent = MatkulEvent(),
    val isEntryValid: FormErrorStateMk = FormErrorStateMk(),
    val snackBarMessage: String? = null,
    val dosenList: List<Dosen> = emptyList(),
    val isLoading: Boolean = false
)

data class FormErrorStateMk(
    val kdMk: String? = null,
    val namaMk: String? = null,
    val sks: String? = null,
    val smstr: String? = null,
    val jenis: String? = null,
    val dospem: String? = null
) {
    fun isValid(): Boolean {
        return kdMk == null && namaMk == null && sks == null &&
                smstr == null && jenis == null && dospem == null
    }
}

data class MatkulEvent(
    val kdMk: String = "",
    val namaMk: String = "",
    val sks: String = "",
    val smstr: String = "",
    val jenis: String = "",
    val dospem: String = ""
)

// Menyimpan input form ke dalam entity
fun MatkulEvent.toMatkulEntity(): Matkul = Matkul(
    kdMk = kdMk, // Yang kiri punya entitas, yang kanan punya event
    namaMk = namaMk,
    sks = sks,
    smstr = smstr,
    jenis = jenis,
    dospem = dospem
)