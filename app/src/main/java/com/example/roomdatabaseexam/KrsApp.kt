package com.example.roomdatabaseexam

import android.app.Application
import com.example.roomdatabaseexam.dependencies.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}