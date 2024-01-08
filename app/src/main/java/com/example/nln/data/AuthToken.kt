package com.example.nln.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "AuthToken-table")
data class AuthToken(
    @PrimaryKey()
    val id: Long = 0L,
    @ColumnInfo(name = "Token")
    val token: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    )


