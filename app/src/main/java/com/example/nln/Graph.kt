package com.example.nln

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.nln.data.AuthTokenDataBase
import com.example.nln.data.AuthTokenRes


object Graph {
    private lateinit var database: AuthTokenDataBase

    val authTokenRes by lazy{
        AuthTokenRes(database.authTokenDao())
    }


    fun provide(context: Context){
        database = databaseBuilder(context, AuthTokenDataBase::class.java,"AuthToken.db").build()
    }
}