package com.example.roomdatabaseexam.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabaseexam.Data.dao.DosenDao
import com.example.roomdatabaseexam.Data.dao.MatkulDao
import com.example.roomdatabaseexam.Entity.Dosen
import com.example.roomdatabaseexam.Entity.Matkul

@Database(entities = [Dosen::class, Matkul::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase()
{

    // Mendefinisikan fungsi untuk mengakses DAO
    abstract fun dosenDao(): DosenDao
    abstract fun matkulDao(): MatkulDao

    companion object {
        @Volatile
        private var INSTANCE: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java, // Class database
                    "KrsDatabase" // Nama database
                ).build().also { INSTANCE = it }
            }
        }
    }
}