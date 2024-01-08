package com.example.nln.ProfileView


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.nln.R
import com.example.nln.Services.FirebaseService
import com.example.nln.Services.ImageData
import com.example.nln.Services.firebaseAuthServices
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.data.AuthToken
import com.example.nln.ui.theme.cooperRegular

@Composable
fun ProfileView(
    authTokenViewModel: AuthTokenViewModel
) {

    val email = authTokenViewModel.AuthToken.collectAsState(initial = AuthToken(0, ""))

    val imageUri = rememberSaveable {
        mutableStateOf("")
    }
    val painter =
        rememberAsyncImagePainter(imageUri.value.ifEmpty { "" })

    val lancher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri.let { imageUri.value = it.toString() }
        }

    Box(
        Modifier
            .fillMaxWidth()
            .height(330.dp)
            .background(Color.White),
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
                    painter = painter,
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
                colorResource(id = R.color.backgroundProfile),
                shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = email.value.email, fontWeight = FontWeight.Bold,
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
                        lancher.launch("image/*")
                        if(imageUri.value.isNotEmpty()){
                            firebaseAuthServices.uploadImage(ImageData(imageUri.value))
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(140.dp))


        }
    }
}
