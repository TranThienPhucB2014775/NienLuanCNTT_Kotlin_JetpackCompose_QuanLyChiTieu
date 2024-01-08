package com.example.nln.AuthView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.nln.R
import com.example.nln.Screen
import com.example.nln.ui.theme.cooperRegular

@Composable
fun IntroView(navController: NavController) {
    Column(
        Modifier.padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = "",
            tint = colorResource(id = R.color.boderimage),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = "Smart Money",
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = cooperRegular,
            color = colorResource(id = R.color.boderimage),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Brighter Future",
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = cooperRegular,
            color = colorResource(id = R.color.boderimage),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Money Care: Your personal finance manager. Track expenses, set budgets, gain financial insights. Simplify money management.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = cooperRegular,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate(Screen.DrawScreen.Login.route) {
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
                text = "LOGIN",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Normal,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
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
            colors = ButtonDefaults.buttonColors(Color.DarkGray)
        ) {
            Text(
                text = "SIGN UP",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.boderimage)
            )
        }
    }
}