package com.example.nln.AuthView

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.nln.ViewModels.AuthViewModel
import com.example.nln.ui.theme.cooperRegular
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun SignUpView(navController: NavController, modifier: Modifier = Modifier) {
    val email = remember {
        mutableStateOf("")
    }
    val pass = remember {
        mutableStateOf("")
    }
    val comfirmPass = remember {
        mutableStateOf("")
    }
    val showDiaLog = remember {
        mutableStateOf(false)
    }
    val contentDiaLog = remember {
        mutableStateOf("")
    }
    val isSignUp = remember {
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
            text = "Create new",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            color = colorResource(id = R.color.boderimage),
            fontSize = 42.sp,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Account",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            color = colorResource(id = R.color.boderimage),
            fontSize = 42.sp,
        )

        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Text(
                text = "Already Registered?",
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = cooperRegular,
                color = colorResource(id = R.color.boderimage),
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Login",
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navController.navigate(Screen.DrawScreen.Login.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                fontFamily = cooperRegular,
                color = Color.Black,
                fontSize = 24.sp,
            )
        }


        TextField(label = "Email", value = email.value, false, OnValueChanged = {
            email.value = it
        })
        TextField(label = "Password", value = pass.value, true) {
            pass.value = it
        }
        TextField(label = "Confirm Password", value = comfirmPass.value, true) {
            comfirmPass.value = it
        }

        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                GlobalScope.launch {
                    validateForm(
                        authViewModel,
                        email.value,
                        pass.value,
                        comfirmPass.value,
                        { showDiaLog.value = true },
                        { contentDiaLog.value = it },
                        { isSignUp.value = true }
                    )
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
            isSignUp.value,
            navController
        ) {
            showDiaLog.value = false
        }
    }
    when {
        authViewModel.authState.value.loading -> {
            if (isSignUp.value) {
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
//                        CircularProgressIndicator()
                        CircularProgressIndicator(Modifier.height(50.dp))
                    }
                }
            }

        }

        else -> {
            if (isSignUp.value) {
                showDiaLog.value = true
                DialogView(
                    authViewModel.authState.value.message,
                    false,
                    navController
                ) {
                    showDiaLog.value = false
                    isSignUp.value = false
                    authViewModel
                }
                showDiaLog.value = false
            }
        }
    }

}

private suspend fun validateForm(
    authViewModel: AuthViewModel,
    email: String,
    pass: String,
    comfirmPass: String,
    showDialog: () -> Unit,
    changeContentDiaLog: (String) -> Unit,
    isSignUp: () -> Unit
) {
    if (email.isEmpty() || pass.isEmpty() || comfirmPass.isEmpty()) {
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

    if (pass != comfirmPass) {
        changeContentDiaLog("Password and comfirm passord í not match")
        showDialog()
        return
    }
    GlobalScope.launch {
        authViewModel.SignUp(email = email, pass = pass)
        isSignUp()
    }
    return
}