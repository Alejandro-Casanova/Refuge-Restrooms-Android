package com.example.refugerestrooms

import android.app.Application
import com.example.refugerestrooms.data.AppContainer
import com.example.refugerestrooms.data.DefaultAppContainer

class RefugeRestroomsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}