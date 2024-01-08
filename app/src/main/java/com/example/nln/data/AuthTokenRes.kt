package com.example.nln.data


import kotlinx.coroutines.flow.Flow

class AuthTokenRes(private val authTokenDao: AuthTokenDao) {
    suspend fun addAuthToken(authToken: AuthToken){
        authTokenDao.addAuthTokenDao((authToken))
    }

    suspend fun updateAuthToken(authToken: AuthToken){
        authTokenDao.updateAuthTokenDao(authToken)
    }

    suspend fun deleteAuthToken(authToken: AuthToken){
        authTokenDao.deleteAuthTokenDao(authToken)
    }

    fun getAuthToken(): Flow<AuthToken> = authTokenDao.getAuthTokenDao()
}