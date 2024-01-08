package com.example.nln.AuthView

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nln.R
import com.example.nln.Screen
import com.example.nln.ViewModels.AuthTokenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AuthView(
    authTokenViewModel: AuthTokenViewModel,
) {
    val navController = rememberNavController()
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
    ) {
        Image(
            painter = painterResource(R.drawable.backgroundauth),
            contentDescription = "",
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(colorResource(R.color.background), Color.White),
                        startY = 600f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        NavHost(
            navController,
            startDestination = Screen.DrawScreen.Intro.route
        ) {
            composable(Screen.DrawScreen.Login.route) { LoginView(navController,authTokenViewModel) }
            composable(Screen.DrawScreen.SignUp.route) { SignUpView(navController) }
            composable(Screen.DrawScreen.Intro.route) { IntroView(navController)  }
        }

    }
}
