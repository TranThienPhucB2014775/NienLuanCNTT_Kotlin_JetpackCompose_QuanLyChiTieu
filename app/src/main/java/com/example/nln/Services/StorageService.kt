package com.example.nln.Services

import com.example.nln.ContextProvider
import com.example.nln.R
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


val interceptorStorage = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

val clientStorage = OkHttpClient.Builder().addInterceptor(interceptorStorage).build()

val baseUrlStorage = ContextProvider.getString(R.string.BUCKET_NAME)

val retrofitStorage: Retrofit = Retrofit.Builder()
    .baseUrl(baseUrlStorage)
    .addConverterFactory(GsonConverterFactory.create())
    .client(clientStorage)
    .build()

val firebaseStorageService: FirebaseStorageService = retrofitStorage.create(FirebaseStorageService::class.java)


interface FirebaseStorageService {
    @POST("{name}.jpg")
    suspend fun uploadImage(
        @Path("name") name: String,
        @Header("Authorization") token: String,
        @Body file: RequestBody
    )
}



