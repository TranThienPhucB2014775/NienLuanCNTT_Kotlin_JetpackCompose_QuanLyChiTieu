package com.example.nln.Services

import com.example.nln.ContextProvider
import com.example.nln.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor

private val interceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

val baseUrlAuth = ContextProvider.getString(R.string.FIREBASE_API_KEY)

private val retrofit = Retrofit.Builder()
    .baseUrl("https://identitytoolkit.googleapis.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val firebaseAuthService = retrofit.create(FirebaseAuthService::class.java)

interface FirebaseAuthService {
    @POST("/v1/accounts:signInWithPassword")
    suspend fun signInWithEmailAndPassword(
        @Query("key") apiKey: String,
        @Body requestBody: SignInRequestBody
    ): ResponseBody

    @POST("/v1/accounts:signUp")
    suspend fun createUserWithEmailAndPassword(
        @Query("key") apiKey: String,
        @Body requestBody: SignUpRequestBody
    ): ResponseBody
}


data class SignInRequestBody(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

data class SignInResponse(val token: String)

data class SignUpRequestBody(val email: String, val password: String)
data class SignUpResponse(val success: Boolean)

// Cách sử dụng:
// val signInResponse = firebaseAuthService.signInWithEmailAndPassword("YOUR_API_KEY", SignInRequestBody("email", "password"))
// val signUpResponse = firebaseAuthService.createUserWithEmailAndPassword("YOUR_API_KEY", SignUpRequestBody("email", "password"))
