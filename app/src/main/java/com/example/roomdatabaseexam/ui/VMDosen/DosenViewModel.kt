package com.example.roomdatabaseexam.ui.VMDosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseexam.Data.repository.RepositoryDosen
import com.example.roomdatabaseexam.Entity.Dosen
import kotlinx.coroutines.launch

class  DosenViewModel(private val repositoryDsn: RepositoryDosen) : ViewModel() {
    var uiState by mutableStateOf(DosenUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent) {
        uiState = uiState.copy(
            dosenEvent = dosenEvent,

            )
    }
    private fun validateFields(): Boolean {
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            Nidn = if(event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            Nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if(event.JenisKelamin.isNotEmpty()) null else "jenis kelamin tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()

    }
    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDsn.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(), //reset input form
                        isEntryValid = FormErrorState() //Reset error State
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "input tidak valid. periksa kembali data anda"
            )
        }

    }
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}
data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null ,
)

data class FormErrorState(
    val Nidn: String? = null,
    val Nama: String? = null,
    val jenisKelamin: String? = null
){
    fun isValid(): Boolean {
        return Nidn == null && Nama == null && jenisKelamin == null
    }

}


data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val JenisKelamin: String = "",

    )

//Menyimpan input form ke dalam entity
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn, //yang kiri punya entitas, yang kanan punya event
    nama = nama,
    JenisKelamin = JenisKelamin,
)