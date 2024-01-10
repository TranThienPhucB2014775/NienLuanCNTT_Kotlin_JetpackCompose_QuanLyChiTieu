package com.example.nln.Services

import androidx.core.content.ContextCompat
import com.example.nln.ContextProvider
import com.example.nln.Models.ExpenseRecord
import com.example.nln.R
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


val baseUrlExpense = ContextProvider.getString(R.string.FIREBASE_RTDB_URL)


private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrlExpense)
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
        @Path("id") token: String,
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