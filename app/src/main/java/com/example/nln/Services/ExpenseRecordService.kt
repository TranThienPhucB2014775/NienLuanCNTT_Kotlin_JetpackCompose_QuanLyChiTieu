package com.example.nln.Services

import com.example.nln.Models.ExpenseRecord
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

private val interceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://nien-9835a-default-rtdb.asia-southeast1.firebasedatabase.app/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val firebaseExpenseRecordService = retrofit.create(FirebaseExpenseRecordService::class.java)

interface FirebaseExpenseRecordService {
    @GET("/expenseRecors/{id}.json")
    suspend fun getAllExpenseRecord(
        @Path("id") id: String,
        @Query("auth") apiKey: String,
    ): ResponseBody

    @POST("/expenseRecors/{id}.json")
    suspend fun addExpenseRecor(
        @Path("id") token:String,
        @Query("auth") auth: String,
        @Body requestBody: ExpenseRecord
    ): ResponseBody

    @PATCH("/expenseRecors/{token}/{id}.json")
    suspend fun updateExpenseRecord(
        @Path("token") token: String,
        @Path("id") id: String,
        @Query("auth") auth: String,
        @Body requestBody: ExpenseRecord
    ): ResponseBody
    @DELETE("/expenseRecors/{token}/{id}.json")
    suspend fun deleteExpenseRecord(
        @Path("token") token: String,
        @Path("id") id: String,
        @Query("auth") apiKey: String,
    ): ResponseBody
}