package com.example.roomdatabaseexam.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Matkul")
class Matkul (
    @PrimaryKey
    val kdMk: String,
    val namaMk: String,
    val sks: String,
    val smstr: String,
    val jenis: String,
    val dospem: String
)