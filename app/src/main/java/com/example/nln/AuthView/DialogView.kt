package com.example.nln.AuthView

//import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.nln.R
import com.example.nln.Screen
import com.example.nln.ui.theme.cooperRegular

@Composable
fun DialogView(
    content: String,
    isSignUp: Boolean,
    navController: NavController,
    showDiaLog: () -> Unit,

) {
    AlertDialog(
        onDismissRequest = {
            showDiaLog()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    showDiaLog()
                }
            ) {
                Text(
                    "Close", fontFamily = cooperRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        },
        title = {
            Text(
                text = "Warning",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.boderimage)
            )
        },
        text = {
            Text(
                text = content,
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        },
        dismissButton = {
            if (isSignUp) {
                TextButton(
                    onClick = {
                        showDiaLog()
                        navController.navigate(Screen.DrawScreen.Login.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Text(
                        "LOGIN",
                        fontFamily = cooperRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}