package com.example.nln.Homeview

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import com.example.nln.R
import com.example.nln.Screen
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.data.AuthToken
import com.example.nln.Partials.ListTransactionView
import com.example.nln.ui.theme.cooperRegular
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeView(
    navController: NavController,
    authTokenViewModel: AuthTokenViewModel,
    expenseRecorViewModel: ExpenseRecorViewModel,
    context: Context
) {


    val authToken = authTokenViewModel.AuthToken.collectAsState(initial = AuthToken(0, ""))

    var painterUrl =
        context.getString(R.string.BUCKET_NAME) +
                if (authToken.value.token.isEmpty()) "1" else authToken.value.token +
                        ".jpg?alt=media"

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
//            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    painterUrl,
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .border(
                        BorderStroke(2.dp, color = colorResource(id = R.color.boderimage)),
                        CircleShape
                    )
                    .padding(2.dp)
                    .clip(CircleShape)
            )
            Text(
                text = authToken.value.email,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = cooperRegular,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Total Balance",
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "$ ${
                NumberFormat.getNumberInstance(Locale.US)
                    .format(expenseRecorViewModel.countTotal(expenseRecorViewModel.expenseRecorState.value.list))
            }",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                ButtonMainViewRight() {
                    navController.navigate(Screen.DrawScreen.AddEditInc.route + "/empty") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)

            ) {
                ButtonMainViewLeft() {
                    navController.navigate(Screen.DrawScreen.AddEditExp.route + "/empty") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Recent",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 8.dp),
            fontFamily = cooperRegular,
            fontSize = 24.sp
        )
        if (expenseRecorViewModel.expenseRecorState.value.loading) {
            CircularProgressIndicator(
                Modifier
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            if (expenseRecorViewModel.expenseRecorState.value.list.expenseRecords.size >= 4) {
                ListTransactionView(
                    navController,
                    expenseRecorViewModel.expenseRecorState.value.list.expenseRecords.takeLast(4)
                        .reversed(),
                    expenseRecorViewModel,
                )
            } else {
                ListTransactionView(
                    navController,
                    expenseRecorViewModel.expenseRecorState.value.list.expenseRecords,
                    expenseRecorViewModel,
                )
            }

        }

    }
}