package com.example.roomdatabaseexam.ui.View.Matkul

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabaseexam.Entity.Matkul
import com.example.roomdatabaseexam.ui.VmMatkul.HomeMatkulViewModel
import com.example.roomdatabaseexam.ui.VmMatkul.HomeMkUiState
import com.example.roomdatabaseexam.ui.VmMatkul.PenyediaMatkul
import com.example.roomdatabaseexam.ui.customWidget.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeMk(
    viewModel: HomeMatkulViewModel = viewModel(factory = PenyediaMatkul.Factory),
    onAddMk: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    judul = "Daftar MataKuliah",
                    showBackButton = false,
                    onBack = { },
                    modifier = modifier
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMk,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Matkul"
                )
            }
        }
    ) { innerPadding ->
        val homeMkUiState by viewModel.homeMkUIState.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0)) // Warna abu muda untuk latar belakang utama
        ) {
            BodyHomeMk(
                homeMkUiState = homeMkUiState,
                onClick = { onDetailClick(it) },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
@Composable
fun BodyHomeMk(
    homeMkUiState: HomeMkUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when {
        homeMkUiState.isloading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeMkUiState.isError -> {
            LaunchedEffect(homeMkUiState.errorMessage) {
                homeMkUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = homeMkUiState.errorMessage ?: "Terjadi kesalahan.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        homeMkUiState.listMk.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data MataKuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListMatkul(
                listMk = homeMkUiState.listMk,
                onClick = onClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMatkul(
    listMk: List<Matkul>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(modifier = modifier) {
        items(
            items = listMk,
            itemContent = { mk ->
                CardMk(
                    mk = mk,
                    onClick = { onClick(mk.kdMk) }
                )
            }
        )
    }

}

@Composable
fun CardMk(
    mk: Matkul,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF001F54) // Warna navy untuk latar belakang kartu
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = mk.kdMk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White // Teks putih
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Email, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = mk.namaMk,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White // Teks putih
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${mk.sks} SKS",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White // Teks putih
                )
            }
        }
    }
}