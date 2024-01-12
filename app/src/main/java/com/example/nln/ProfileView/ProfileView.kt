package com.example.nln.ProfileView

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.nln.R
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.ViewModels.StorageViewModel
import com.example.nln.data.AuthToken
import com.example.nln.ui.theme.cooperRegular
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@SuppressLint("UnrememberedMutableState")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ProfileView(
    authTokenViewModel: AuthTokenViewModel,
    context: Context
) {

    val authToken = authTokenViewModel.AuthToken.collectAsState(initial = AuthToken(0, ""))

    val storageViewModel: StorageViewModel = viewModel()

    val imageUri = remember {
        mutableStateOf("")
    }
    var painterUrl =
        context.getString(R.string.BUCKET_NAME) +
                if (authToken.value.token.isEmpty()) "1" else authToken.value.token +
                        ".jpg?alt=media"

    var uriImg = Uri.parse(imageUri.value)

    val isAddOrChangeAvatar = remember {
        mutableStateOf(false)
    }

    val triggerImageRender = remember {
        mutableStateOf(0)
    }

    if (isAddOrChangeAvatar.value) {
        if (storageViewModel.storageState.value.loading) {
            Dialog(onDismissRequest = {
            }) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator(Modifier.height(50.dp))
                }
            }

        } else {
            if (!storageViewModel.storageState.value.error) {
                Toast.makeText(context, "Avatar has been update", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Error, Please try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            isAddOrChangeAvatar.value = false
        }
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri.let { it ->
                imageUri.value = it.toString()
                if (it != null) {
                    uriImg = it
                }
                val filePath = getPathFromUri(
                    context,
                    uriImg
                )
                val file = filePath?.let { File(it) }

                val requestFile =
                    file?.asRequestBody("image/*".toMediaTypeOrNull())

                if (requestFile != null) {
                    isAddOrChangeAvatar.value = true
                    GlobalScope.launch {
                        storageViewModel.addStorage(requestFile, authToken.value.token)
                    }
                }
            }
        }

    Box(
        Modifier
            .fillMaxWidth()
            .height(330.dp)
            .background(colorResource(id = R.color.background)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(150.dp, 150.dp)
                    .aspectRatio(1f)
                    .background(
                        Color(0xFFFEA37E), shape = RoundedCornerShape(50.dp)
                    )
                    .clip(RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        painterUrl,
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 300.dp)
//            .height(700.dp)
            .background(
                colorResource(id = R.color.background),
//                shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = authToken.value.email, fontWeight = FontWeight.Bold,
                fontFamily = cooperRegular,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(
                Modifier
                    .shadow(
                        elevation = 5.dp,
//                        clip = Boolean,
                        ambientColor = Color.Gray,
                        spotColor = Color.Black,
                        shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
                    )
                    .width(380.dp)
//                    .height(300.dp)
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)
                    ),
            ) {
                MenuView(
                    authTokenViewModel,
                    { authTokenViewModel.addAuthToken(AuthToken(1, "", "")) },
                    {
                        val storageActivity = StorageActivity(context)

                        if (storageActivity.checkStoragePermission()) {
                            GlobalScope.launch {
                                launcher.launch("image/*")
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Permission to need grant",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            storageActivity.requestStoragePermission()
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(140.dp))


        }
    }
}

fun getPathFromUri(context: Context, uri: Uri): String? {
    var filePath: String? = null
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val outputFile = File(context.cacheDir, "tempFile.jpg")
            val outputStream = FileOutputStream(outputFile)
            input.copyTo(outputStream)
            filePath = outputFile.absolutePath
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return filePath
}







