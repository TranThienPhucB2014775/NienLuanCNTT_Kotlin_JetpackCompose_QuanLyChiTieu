package com.example.nln

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nln.AuthView.AuthView
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.data.AuthToken
import com.example.nln.ui.theme.NLNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextProvider.setContext(this)
//        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {

            NLNTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(this)
                }
            }
        }
    }
}

@Composable
 fun Navigation(context: Context) {

    val authTokenViewModel: AuthTokenViewModel = viewModel()

    val showSplash = remember {
        mutableStateOf(true)
    }
    if (showSplash.value) {
        Splash()
    }
    val Auth: String = authTokenViewModel.AuthToken.collectAsState(initial = AuthToken(0, "None")).value?.token ?: ""
    if (Auth.isNotEmpty() && Auth != "None") {
        showSplash.value = false
        MainView(authTokenViewModel,Auth,context)
    } else if (Auth != "None"){
        showSplash.value = false
        AuthView(authTokenViewModel)
    }
}