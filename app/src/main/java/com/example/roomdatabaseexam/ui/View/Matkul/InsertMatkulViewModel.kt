package com.example.roomdatabaseexam.ui.View.Matkul

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabaseexam.Entity.Dosen
import com.example.roomdatabaseexam.ui.Navigasi.AlamatNavigasi
import com.example.roomdatabaseexam.ui.VmMatkul.FormErrorStateMk
import com.example.roomdatabaseexam.ui.VmMatkul.MatkulEvent
import com.example.roomdatabaseexam.ui.VmMatkul.MatkulViewModel
import com.example.roomdatabaseexam.ui.VmMatkul.MkUIState
import com.example.roomdatabaseexam.ui.VmMatkul.PenyediaMatkul
import com.example.roomdatabaseexam.ui.customWidget.TopAppBar
import kotlinx.coroutines.launch

object DestinasiInsertMatkul : AlamatNavigasi {
    override val route: String = "insertMk"
}

@Composable
fun InsertMatkul(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatkulViewModel = viewModel(factory = PenyediaMatkul .Factory)
)
{
    val uiStateMk = viewModel.uiStateMk //ambil UI state dari viewmodel
    val snackbarHostState = remember   { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    //Observasi perubahan snackbarMessage
    LaunchedEffect(uiStateMk.snackBarMessage) {
        uiStateMk.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //tampilkan sncakbar
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah"
            )
            // Isi Body
            InsertBodyMk(
                uiStateMk = uiStateMk,
                onValueChange = { updatedEvent ->
                    viewModel.updateStateMatkul(updatedEvent) // update state di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData() // simpan data
                    }
                    onNavigate()
                },
                dosenList = uiStateMk.dosenList // Pass dosenList from uiStateMk
            )

        }
    }
}

@Composable
fun InsertBodyMk(
    modifier: Modifier = Modifier,
    onValueChange: (MatkulEvent) -> Unit,
    uiStateMk: MkUIState,
    onClick: () -> Unit,
    dosenList: List<Dosen>  // Pass dosenList here
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatkul(
            matkulEvent = uiStateMk.matkulEvent,
            onValueChange = onValueChange,
            errorState = uiStateMk.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList  // Use dosenList here
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatkul(
    matkulEvent: MatkulEvent = MatkulEvent(),
    onValueChange: (MatkulEvent) -> Unit,
    errorState: FormErrorStateMk = FormErrorStateMk(),
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>
) {
    var chosenDropdown by remember { mutableStateOf(matkulEvent.dospem) }
    var expanded by remember { mutableStateOf(false) }

    val jenis = listOf("Wajib", "Peminatan")

    Column (
        modifier = modifier.fillMaxWidth()
    ){

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.namaMk,
            onValueChange = {
                onValueChange(matkulEvent.copy(namaMk = it))
            },
            label = { Text("Matakuliah") },
            isError = errorState.namaMk != null,
            placeholder = {Text("Masukkan Matakuliah")},

            )
        Text(
            text = errorState.namaMk ?:"",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.kdMk, onValueChange = {
                onValueChange(matkulEvent.copy(kdMk = it))

            },
            label = { Text("Kode") },
            isError = errorState.kdMk != null,
            placeholder = {Text("Masukkan Kode Matkul")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.kdMk ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.sks,
            onValueChange = {
                onValueChange(matkulEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = {Text("Masukkan Jumlah SKS")},

            )
        Text(
            text = errorState.sks ?:"",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matkulEvent.smstr,
            onValueChange = {
                onValueChange(matkulEvent.copy(smstr = it))
            },
            label = { Text("SEMESTER") },
            isError = errorState.smstr != null,
            placeholder = {Text("Masukkan Semester")},

            )
        Text(
            text = errorState.smstr ?:"",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis MataKuliah")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            jenis.forEach{ jm ->
                Row  (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = matkulEvent.jenis == jm,
                        onClick = {
                            onValueChange(matkulEvent.copy(jenis = jm))
                        },
                    )

                    Text(
                        text = jm,
                    )
                }
            }
        }
        Text(
            text = errorState.jenis?: "",
            color = Color.Red
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = chosenDropdown,
                onValueChange = { /* Cannot be changed manually */ },
                label = { Text("Pilih Dosen Pengampu") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand menu"
                    )
                },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                isError = errorState.dospem != null
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        onClick = {
                            chosenDropdown = dosen.nama
                            expanded = false
                            onValueChange(matkulEvent.copy(dospem = dosen.nama))
                        },
                        text = { Text(text = dosen.nama) }
                    )
                }
            }
        }
        Text(text = errorState.dospem ?: "", color = Color.Red)


    }
}