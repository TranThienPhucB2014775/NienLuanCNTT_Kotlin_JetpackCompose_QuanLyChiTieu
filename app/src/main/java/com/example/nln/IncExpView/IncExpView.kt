package com.example.nln.IncExpView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Divider
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nln.R
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.Partials.ListTransactionView

@Composable
fun IncExpView(
    expenseRecorViewModel: ExpenseRecorViewModel
) {
    val selected = remember {
        mutableStateOf("income")
    }
    CircularProgressIndicator()
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(top = 30.dp, start = 16.dp, end = 16.dp),
    ) {
        Column {
            Row {
                Text(
                    text = "Income",
                    color = if (selected.value == "income") colorResource(id = R.color.boderimage) else Color.Gray,
                    modifier = Modifier.clickable {
                        selected.value = "income"
                        navController.navigate(selected.value) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "Expenses",
                    color = if (selected.value == "expenses") colorResource(id = R.color.boderimage) else Color.Gray,
                    modifier = Modifier
                        .clickable {
                            selected.value = "expenses"
                            navController.navigate(selected.value) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    Modifier
                        .width(85.dp)
                        .height(
                            if (selected.value == "income") 4.dp else 2.dp
                        ),
                    color = colorResource(id = R.color.boderimage)
                )
                Divider(
                    Modifier
                        .width(32.dp)
                        .height(2.dp),
                    color = colorResource(id = R.color.boderimage)
                )
                Divider(
                    Modifier
                        .width(100.dp)
                        .height(
                            if (selected.value == "expenses") 4.dp else 2.dp
                        ),
                    color = colorResource(id = R.color.boderimage)
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = colorResource(id = R.color.boderimage)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            NavHost(
                navController,
                startDestination = selected.value,
            ) {
                composable("income") {
                    ListTransactionView(
                        navController,
                        expenseRecorViewModel.getInc(expenseRecorViewModel.expenseRecorState.value.list).expenseRecords.reversed(),
                        expenseRecorViewModel,
                    )
                }
                composable("expenses") {
                    ListTransactionView(
                        navController,
                        expenseRecorViewModel.getExp(expenseRecorViewModel.expenseRecorState.value.list).expenseRecords.reversed(),
                        expenseRecorViewModel,
                    )
                }

            }
        }
    }
}
