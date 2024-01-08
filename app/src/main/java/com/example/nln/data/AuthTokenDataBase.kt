package com.example.nln.data


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AuthToken::class],
    version = 1,
    exportSchema = false
)
abstract class AuthTokenDataBase: RoomDatabase() {
    abstract fun authTokenDao(): AuthTokenDao
}