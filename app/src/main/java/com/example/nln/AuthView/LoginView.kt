package com.example.nln.AuthView

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.nln.R
import com.example.nln.Screen
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.ViewModels.AuthViewModel
import com.example.nln.data.AuthToken
import com.example.nln.ui.theme.cooperRegular
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    navController: NavController,
    authTokenViewModel: AuthTokenViewModel,
) {
    val email = remember {
        mutableStateOf("")
    }
    val pass = remember {
        mutableStateOf("")
    }
    val showDiaLog = remember {
        mutableStateOf(false)
    }
    val contentDiaLog = remember {
        mutableStateOf("")
    }
    val isSignIn = remember {
        mutableStateOf(false)
    }
    val authViewModel: AuthViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(158.dp))
        Text(
            text = "Login",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            color = colorResource(id = R.color.boderimage),
            fontSize = 42.sp,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Sign in to Continue",
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            color = colorResource(id = R.color.boderimage),
            fontSize = 24.sp,
        )
        TextField(label = "Email", value = email.value, false, OnValueChanged = {
            email.value = it
        })
        TextField(label = "Pass", value = pass.value, true) {
            pass.value = it
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                validateForm(
                    authViewModel,
                    email.value,
                    pass.value,
                    { showDiaLog.value = true },
                    { contentDiaLog.value = it },
                    { isSignIn.value = true }
                )
            },
            Modifier
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage))
        ) {
            Text(
                text = "LOGIN",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Normal,
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                navController.navigate(Screen.DrawScreen.SignUp.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            Modifier
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage))
        ) {
            Text(
                text = "SIGN UP",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Normal,
            )
        }
    }
    if (showDiaLog.value) {
        DialogView(
            contentDiaLog.value,
            false,
            navController
        ) { showDiaLog.value = false }
    }

    when {
        authViewModel.authState.value.loading -> {
            if (isSignIn.value) {
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    Box(
                        contentAlignment = Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator(Modifier.height(50.dp))
                    }
                }
            }
        }

        authViewModel.authState.value.error -> {
            if (isSignIn.value) {
                showDiaLog.value = true
                Log.d("123456", authViewModel.authState.value.message)
                contentDiaLog.value = authViewModel.authState.value.message
                DialogView(
                    contentDiaLog.value,
                    false,
                    navController
                ) {
                    showDiaLog.value = false
                    isSignIn.value = false
                    authViewModel
                }
//                showDiaLog.value = false
                isSignIn.value = false
                authViewModel.initAuthState()
            }
        }

        else -> {
            if (isSignIn.value) {
                authTokenViewModel.addAuthToken(
                    AuthToken(
                        1,
                        authViewModel.authState.value.message,
                        email.value
                    )
                )
                isSignIn.value = false
                email.value = ""
                pass.value = ""
                authViewModel.initAuthState()
//                contentDiaLog.value = ""
            }
        }
    }
}

private fun validateForm(
    authViewModel: AuthViewModel,
    email: String,
    pass: String,
    showDialog: () -> Unit,
    changeContentDiaLog: (String) -> Unit,
    isSignIn: () -> Unit
) {
    if (email.isEmpty() || pass.isEmpty()) {
        changeContentDiaLog("Email, password and confirm password can not empty")
        showDialog()
        return
    }

    val emailPattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")

    if (!email.matches(emailPattern)) {
        changeContentDiaLog("Email is not true")
        showDialog()
        return
    }

    if (pass.length < 8) {
        changeContentDiaLog("Password is too short")
        showDialog()
        return
    }
    GlobalScope.launch {
        isSignIn()
        authViewModel.logIn(email, pass)
    }

    return
}