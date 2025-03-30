package com.example.phinmaedapp

import android.app.Application
import android.content.Context

class ApplicationClass : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}