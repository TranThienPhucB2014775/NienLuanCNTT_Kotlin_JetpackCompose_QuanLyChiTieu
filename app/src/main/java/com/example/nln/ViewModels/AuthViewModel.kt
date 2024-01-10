package com.example.nln.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nln.ContextProvider
import com.example.nln.R
import com.example.nln.Services.SignInRequestBody
import com.example.nln.Services.SignUpRequestBody
import com.example.nln.Services.firebaseAuthService
import kotlinx.coroutines.launch
import org.json.JSONObject

class AuthViewModel : ViewModel() {
    private val _AuthState = mutableStateOf(AuthState())

    val authState: State<AuthState> = _AuthState

    val apiKey = ContextProvider.getString(R.string.FIREBASE_API_KEY)

    fun initAuthState() {
        _AuthState.value = _AuthState.value.copy(
            loading = true,
            message = "",
            error = false
        )
    }

    suspend fun logIn(
        email: String,
        pass: String
    ) {
        viewModelScope.launch {
            try {
                val signInResponse = firebaseAuthService.signInWithEmailAndPassword(
                    apiKey,
                    SignInRequestBody(email, pass)
                )
                val ress = JSONObject(signInResponse.string().trimIndent())
                _AuthState.value = _AuthState.value.copy(
                    loading = false,
                    message = ress.getString("localId"),
                    error = false
                )
            } catch (e: Exception) {
                if (e.message.toString() == "HTTP 400 ") {
                    _AuthState.value = _AuthState.value.copy(
                        loading = false,
                        message = "INVALID LOGIN CREDENTIALS",
                        error = true
                    )
                } else if (e.message.toString() == "HTTP 404 ") {
                    _AuthState.value = _AuthState.value.copy(
                        loading = false,
                        message = "HAVE A PROBLEM WHEN CONNECT TO SERVER",
                        error = true
                    )
                    Log.d("123456789", e.message.toString())
                    Log.d("123456789", (e.message.toString() == "HTTP 400 ").toString())
                }
            }
        }
    }

    suspend fun SignUp(
        email: String,
        pass: String
    ) {
        viewModelScope.launch {
            try {
                val SignUpResponse = firebaseAuthService.createUserWithEmailAndPassword(
                    apiKey,
                    SignUpRequestBody(email, pass)
                )
                val ress = JSONObject(SignUpResponse.string().trimIndent())
                _AuthState.value = _AuthState.value.copy(
                    loading = false,
                    message = "Create account $email success",
                    error = false
                )
                Log.d("123456789", ress.getString("localId"))
            } catch (e: Exception) {
                if (e.message.toString() == "HTTP 400 ") {
                    _AuthState.value = _AuthState.value.copy(
                        loading = false,
                        message = "Email has been used",
                        error = true
                    )
                } else if (e.message.toString() == "HTTP 404 ") {
                    _AuthState.value = _AuthState.value.copy(
                        loading = false,
                        message = "HAVE A PROBLEM CONNECT TO SERVER",
                        error = true
                    )
                    Log.d("123456789", e.message.toString())
                    Log.d("123456789", (e.message.toString() == "HTTP 400 ").toString())
                }
            }
        }
    }

    data class AuthState(
        val loading: Boolean = true,
        val message: String = "",
        val error: Boolean = false
    )

}