package com.example.roomdatabaseexam.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dosen")
data class Dosen(
@PrimaryKey
val nidn: String,
val nama: String,
val JenisKelamin: String
)
