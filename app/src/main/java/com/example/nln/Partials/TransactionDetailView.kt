package com.example.nln.Partials

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.nln.Models.ExpenseRecordFireBases
import com.example.nln.Screen
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.ui.theme.cooperRegular
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionDetailView(
    navController: NavController,
    expenseRecorViewModel: ExpenseRecorViewModel,
    expenseRecordFireBase: ExpenseRecordFireBases,
    colorResourceId: Int,
) {
    var pain = painterResource(id = R.drawable.different)
    when (expenseRecordFireBase.expenseRecord.purpose) {
        "Salary" -> pain = painterResource(id = R.drawable.salary)
        "Freelance" -> pain = painterResource(id = R.drawable.freelance)
        "Business" -> pain = painterResource(id = R.drawable.business)
        "Bonuses" -> pain = painterResource(id = R.drawable.bonus)
        "Electricity" -> pain = painterResource(id = R.drawable.electricity)
        "Rent" -> pain = painterResource(id = R.drawable.rent)
        "Grocery" -> pain = painterResource(id = R.drawable.grocery)
        "Transportation" -> pain = painterResource(id = R.drawable.transportation)
        "Entertainment" -> pain = painterResource(id = R.drawable.entertainment)
        "Insurance" -> pain = painterResource(id = R.drawable.insurance)
        "Vacation" -> pain = painterResource(id = R.drawable.vacation)

    }
    val heigh = remember {
        mutableStateOf(100.dp)
    }
    val showDiaLog = remember {
        mutableStateOf(false)
    }
    if (showDiaLog.value) {
        DialogView(
            expenseRecorViewModel,
            { showDiaLog.value = false },
            { expenseRecorViewModel.fetch() },
            expenseRecordFireBase.id
        )
    }
    Row(
        Modifier
            .clickable {
                if (heigh.value == 200.dp) {
                    heigh.value = 100.dp
                } else {
                    heigh.value = 200.dp
                }
            }
            .shadow(
                elevation = 5.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Black,
                shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
            )
            .clip(RoundedCornerShape(20.dp)),
    ) {
        Box(
            Modifier
                .width(250.dp)
                .height(heigh.value)
                .background(
                    colorResource(id = colorResourceId),
                    shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp)
                )
        ) {
            Row(
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = pain,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(5.dp)
                        .height(90.dp)
                        .width(90.dp)
                )
                Column {
                    Text(
                        text = expenseRecordFireBase.expenseRecord.purpose,
                        fontWeight = FontWeight.Bold,
                        fontFamily = cooperRegular,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 15.dp)
                    )
                    Text(
                        text = expenseRecordFireBase.expenseRecord.description,
                        fontWeight = FontWeight.Normal,
                        fontFamily = cooperRegular,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp),
                        maxLines = if (heigh.value == 100.dp) 3 else 8
                    )
                }
            }
        }
        Box(
            Modifier
//                .padding(bottom = 15.dp)
                .fillMaxWidth()
                .height(heigh.value)
                .background(
                    colorResource(id = R.color.boderimage),
                    shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp)
                )
        ) {
            Column(
            ) {
                Text(
                    text = if (SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                            expenseRecordFireBase.expenseRecord.date
                        ) == SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                    ) "Today" else {
                        SimpleDateFormat(
                            "yyyy/MM/dd",
                            Locale.getDefault()
                        ).format(expenseRecordFireBase.expenseRecord.date)
                    },
                    Modifier.padding(start = 10.dp, top = 20.dp),
                    fontWeight = FontWeight.Normal,
                    fontFamily = cooperRegular,
                    fontSize = 20.sp,
                )
                Text(
                    text = if (expenseRecordFireBase.expenseRecord.isIncome) {
                        "+$" + NumberFormat.getNumberInstance(Locale.US)
                            .format(expenseRecordFireBase.expenseRecord.money)
                    } else {
                        "-$" + NumberFormat.getNumberInstance(Locale.US)
                            .format(expenseRecordFireBase.expenseRecord.money)
                    },
//                    Modifier.padding(start = 10.dp, top = 5.dp),
                    fontWeight = FontWeight.Normal,
                    fontFamily = cooperRegular,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Row(
//                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { showDiaLog.value = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = "delete",
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate("${Screen.DrawScreen.AddEditInc.route}/${expenseRecordFireBase.id}") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Edit, contentDescription = "edit",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun DialogView(
    expenseRecorViewModel: ExpenseRecorViewModel,
    unShowDiaLog: () -> Unit,
    fetch: () -> Unit,
    id: String
) {
    AlertDialog(
        onDismissRequest = {
            unShowDiaLog()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    expenseRecorViewModel.deleteExpenseRecors(id)
                    unShowDiaLog()
                }
            ) {
                Text(
                    "Yes", fontFamily = cooperRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    unShowDiaLog()
                }
            ) {
                Text(
                    "No",
                    fontFamily = cooperRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }

        },
        title = {
            Text(
                text = "Alert dialog",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.boderimage)
            )
        },
        text = {
            Text(
                text = "Confirm delete it?",
                fontFamily = cooperRegular,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        },
    )
}