package com.example.nln.Models

data class Auth(val email:String,val pass:String)

data class SignInRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

data class SignInResponse(
    val idToken: String?,
    val refreshToken: String?,
    val expiresIn: String?,
    // ... other properties you might need
)
