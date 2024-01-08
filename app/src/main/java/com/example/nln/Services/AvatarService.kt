package com.example.nln.Services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Base64
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

// Interface Retrofit Service

private val interceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://gs://nien-9835a.appspot.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val firebaseAuthServices = retrofit.create(FirebaseService::class.java)


interface FirebaseService {
    @POST("images.json") // Thay thế "images.json" bằng đường dẫn bạn muốn lưu dữ liệu
    fun uploadImage(@Body image: ImageData): Call<Void>
}

// Class dữ liệu cho ảnh
data class ImageData(
    @SerializedName("base64data") val base64data: String
)

// Hàm để chuyển đổi ảnh thành Base64
fun convertImageToBase64(imageUrl: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://your-firebase-database-url/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(FirebaseService::class.java)

    val downloadImageTask = object : AsyncTask<String?, Void?, Bitmap?>() {
        override fun doInBackground(vararg urls: String?): Bitmap? {
            val url = urls[0]
            var bitmap: Bitmap? = null
            try {
                val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                val outputStream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val byteArray = outputStream.toByteArray()
                val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)

                val imageData = ImageData(base64Image)
                service.uploadImage(imageData).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        // Xử lý khi tải lên thành công
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Xử lý khi gặp lỗi
                    }
                })
            }
        }
    }

    val imageUrl = "URL_CUA_ANH"
    downloadImageTask.execute(imageUrl)
}
