package com.example.nln.ViewModels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nln.Graph
import com.example.nln.data.AuthToken
import com.example.nln.data.AuthTokenRes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AuthTokenViewModel(
    private val authTokenRes: AuthTokenRes = Graph.authTokenRes
) : ViewModel() {

    var Token by mutableStateOf("")

    fun onTokenChange(newString: String) {
        Token = newString
    }

    lateinit var AuthToken: Flow<AuthToken>

    init {
        viewModelScope.launch {
            AuthToken = authTokenRes.getAuthToken()
        }

    }

    fun addAuthToken(authToken: AuthToken) {
        viewModelScope.launch(Dispatchers.IO) {
            authTokenRes.addAuthToken(authToken = authToken)
        }
    }

    fun updateAuthToken(authToken: AuthToken) {
        viewModelScope.launch(Dispatchers.IO) {
            authTokenRes.updateAuthToken(authToken = authToken)
        }
    }

    fun deleteAuthToken(authToken: AuthToken) {
        viewModelScope.launch(Dispatchers.IO) {
            authTokenRes.deleteAuthToken(authToken = authToken)
        }
    }
}