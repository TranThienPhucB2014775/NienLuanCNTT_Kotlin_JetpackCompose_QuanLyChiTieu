package com.example.nln

import android.app.Application

class AuthTokenApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}