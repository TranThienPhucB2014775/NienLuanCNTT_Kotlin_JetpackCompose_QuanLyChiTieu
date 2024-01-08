package com.example.nln.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AuthTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addAuthTokenDao(wishEntity: AuthToken)

    @Query("select * from `authtoken-table` where id = 1")
    abstract fun getAuthTokenDao(): Flow<AuthToken>

    @Update
    abstract suspend fun updateAuthTokenDao(wishEntity: AuthToken)

    @Delete
    abstract suspend fun deleteAuthTokenDao(wishEntity: AuthToken)

}