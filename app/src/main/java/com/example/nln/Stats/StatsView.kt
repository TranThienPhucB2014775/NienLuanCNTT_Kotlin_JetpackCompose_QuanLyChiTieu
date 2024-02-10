package com.example.nln.Stats

import android.content.Context
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.nln.R
import com.example.nln.Screen
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.ui.theme.cooperRegular
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsView(
    expenseRecorViewModel: ExpenseRecorViewModel,
    context: Context
) {
    val showDateTo = remember {
        mutableStateOf(false)
    }
    val showDateFrom = remember {
        mutableStateOf(false)
    }
    val showDialog = remember {
        mutableStateOf(false)
    }
    val dateFrom = remember {
        mutableStateOf(Date())
    }
    val dateTo = remember {
        mutableStateOf(Date())
    }
    val moneyTo = remember {
        mutableStateOf<String?>("0")
    }
    val moneyFrom = remember {
        mutableStateOf<String?>("0")
    }
    val showType = remember {
        mutableStateOf(false)
    }
    val purpose = remember {
        mutableStateOf("All")
    }
    val showIncome = remember {
        mutableStateOf(false)
    }
    val showExpenses = remember {
        mutableStateOf(false)
    }

    val expenseRecords = remember {
        mutableStateOf(
            expenseRecorViewModel.expenseRecorState.value.list.copy()
        )
    }

    val content = remember {
        mutableStateOf("")
    }

    val datePickerStateTo = rememberDatePickerState()

    val datePickerStateFrom = rememberDatePickerState()

    if (showDateTo.value) {
        DatePickerDialog(
            onDismissRequest = { showDateTo.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showDateTo.value = false
                    if (datePickerStateTo.selectedDateMillis != null) {
                        dateTo.value =
                            convertMillisecondsToDate(datePickerStateTo.selectedDateMillis!!)
                    }
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDateTo.value = false }) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(
                state = datePickerStateTo,
            )
        }
    }
    if (showDateFrom.value) {
        DatePickerDialog(
            onDismissRequest = { showDateFrom.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showDateFrom.value = false
                    if (datePickerStateFrom.selectedDateMillis != null) {
                        dateFrom.value =
                            convertMillisecondsToDate(datePickerStateFrom.selectedDateMillis!!)
                    }
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDateFrom.value = false }) {
                    Text("Cancel")
                }
            },
        ) {
            DatePicker(
                state = datePickerStateFrom,
            )
        }
    }
    if (showDialog.value) {
        DialogView(content.value) {
            showDialog.value = false
            content.value = ""
        }
    }
//    ScrollView(context)
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(top = 10.dp, start = 16.dp, end = 16.dp),
//            .verticalScroll(rememberScrollState())
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f),
            ) {
                TextButton(onClick = { showIncome.value = !showIncome.value }) {
                    Text(
                        text = "Income",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cooperRegular,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )
                }

                Text(
                    text = expenseRecorViewModel.countTotal(
                        expenseRecorViewModel.getInc(
                            expenseRecords.value
                        )
                    ).toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    fontSize = 34.sp,
                    color = colorResource(id = R.color.boderimage)
                )
            }
            Divider(
                modifier = Modifier
                    .height(60.dp)
                    .width(1.dp),
                color = Color.Gray
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f),
            ) {
                TextButton(onClick = { showExpenses.value = !showExpenses.value }) {
                    Text(
                        text = "Expense",
                        fontWeight = FontWeight.Bold,
                        fontFamily = cooperRegular,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )
                }
                Text(
                    text = expenseRecorViewModel.countTotal(
                        expenseRecorViewModel.getExp(
                            expenseRecords.value
                        )
                    ).toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    fontSize = 34.sp,
                    color = colorResource(id = R.color.boderimage)
                )
            }
        }
        if (showIncome.value) {
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier.height(1.dp),
                color = Color.Black
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(
                    expenseRecorViewModel.averagePurposeIncome(
                        expenseRecorViewModel.getInc(expenseRecords.value)
                    ).toList()
                ) { (key, value) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.height(25.dp)
                    ) {
                        Text(
                            text = value.name,
                            fontWeight = FontWeight.Normal,
                            fontFamily = cooperRegular,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = value.quantity.toString(),
                            fontWeight = FontWeight.Normal,
                            fontFamily = cooperRegular,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
        if (showExpenses.value) {
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier.height(1.dp),
                color = Color.Black
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(
                    expenseRecorViewModel.averagePurposeExpenses(
                        expenseRecorViewModel.getExp(expenseRecords.value)
                    ).toList()
                ) { (key, value) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = value.name,
                            fontWeight = FontWeight.Normal,
                            fontFamily = cooperRegular,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = value.quantity.toString(),
                            fontWeight = FontWeight.Normal,
                            fontFamily = cooperRegular,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }

                }
            }
        }

        Row {
            OutlinedTextField(
                value = formatDateToString(dateFrom.value),
                onValueChange = {
                    showDateFrom.value = true
                },
                label = {
                    androidx.compose.material.Text(text = "Date from", color = Color.Black)
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(0.35f)
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .clickable {
                        showDateFrom.value = true
                    },
                shape = RoundedCornerShape(30.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    color = Color.Black
                ),
                singleLine = true,
                enabled = false
            )

            OutlinedTextField(
                value = formatDateToString(dateTo.value),
                onValueChange = {
                    showDateTo.value = true
                },
                label = {
                    androidx.compose.material.Text(text = "Date to", color = Color.Black)
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(0.35f)
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .clickable {
                        showDateTo.value = true
                    },
                shape = RoundedCornerShape(30.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    color = Color.Black
                ),
                singleLine = true,
                enabled = false
            )
        }
        Row {
            OutlinedTextField(
                value = moneyFrom.value.toString(),
                onValueChange = {
                    moneyFrom.value = it
                },
                label = {
                    androidx.compose.material.Text(text = "Money from", color = Color.Black)
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(0.35f)
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(30.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    color = Color.Black
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = moneyTo.value.toString(),
                onValueChange = {
                    moneyTo.value = it
                },
                label = {
                    androidx.compose.material.Text(text = "Money to", color = Color.Black)
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(0.35f)
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(30.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    color = Color.Black
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Box(
            Modifier
                .padding(top = 10.dp)
        ) {
            OutlinedTextField(
                value = purpose.value,
                onValueChange = {

                },
                label = {
                    androidx.compose.material.Text(
                        text = "Purpose",
                        color = Color.Black
                    )
                },
                modifier = Modifier
                    .width(190.dp)
                    .height(60.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .clickable {
                        showType.value = true
                    },
                shape = RoundedCornerShape(30.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = cooperRegular,
                    color = Color.Black
                ),
                singleLine = true,
                enabled = false
            )

            DropdownMenu(
                expanded = showType.value,
                onDismissRequest = { showType.value = false }) {
                DropdownMenuItem(
                    text = { Text(text = "All") }, onClick = {
                        purpose.value = "All"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Salary") }, onClick = {
                        purpose.value = "Salary"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Freelance") }, onClick = {
                        purpose.value = "Freelance"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Business") }, onClick = {
                        purpose.value = "Business"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Bonuses") }, onClick = {
                        purpose.value = "Bonuses"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Different") }, onClick = {
                        purpose.value = "Different"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Electricity Bill") }, onClick = {
                        purpose.value = "Electricity Bill"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Rent") }, onClick = {
                        purpose.value = "Rent"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Grocery") }, onClick = {
                        purpose.value = "Grocery"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Transportation") }, onClick = {
                        purpose.value = "Transportation"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Entertainment") }, onClick = {
                        purpose.value = "Entertainment"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Insurance") }, onClick = {
                        purpose.value = "Insurance"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Vacation") }, onClick = {
                        purpose.value = "Vacation"
                        showType.value = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Different ") }, onClick = {
                        purpose.value = "Different"
                        showType.value = false
                    })
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Button(
                onClick = {
                    showDialog.value = validateDate(dateFrom.value, dateTo.value) {
                        content.value = it
                    }
                    if (!showDialog.value) {
                        expenseRecords.value = expenseRecorViewModel.averageDate(
                            expenseRecords.value,
                            dateFrom.value.time,
                            dateTo.value.time
                        )
                        Log.d(
                            "66666666", expenseRecorViewModel.averagePurposeIncome(
                                expenseRecorViewModel.getInc(expenseRecords.value)
                            ).toString()
                        )
                        Log.d(
                            "66666666",
                            expenseRecorViewModel.getExp(expenseRecords.value).toString()
                        )
                        Log.d(
                            "66666666", expenseRecorViewModel.averagePurposeExpenses(
                                expenseRecorViewModel.getExp(expenseRecords.value)
                            ).toString()
                        )

                    }
                },
                modifier = Modifier
                    .weight(0.35f)
                    .height(45.dp),
//                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage)),
                shape = RoundedCornerShape(30.dp),
            ) {
                Text(
                    text = "Date",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = cooperRegular,
                    fontSize = 24.sp

                )
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Button(
                onClick = {
                    showDialog.value = moneyFrom.value?.let {
                        moneyTo.value?.let { it1 ->
                            validateMoney(it.toDouble(), it1.toDouble()) {
                                content.value = it
                            }
                        }
                    } == false
                    if (!showDialog.value) {
                        expenseRecords.value = moneyFrom.value?.let {
                            moneyTo.value?.let { it1 ->
                                expenseRecorViewModel.averageMoney(
                                    expenseRecords.value,
                                    it.toDouble(),
                                    it1.toDouble(),
                                )
                            }
                        }!!
                        Log.d(
                            "66666666", expenseRecorViewModel.averagePurposeIncome(
                                expenseRecorViewModel.getInc(expenseRecords.value)
                            ).toString()
                        )
                        Log.d(
                            "66666666", expenseRecorViewModel.averagePurposeExpenses(
                                expenseRecorViewModel.getExp(expenseRecords.value)
                            ).toString()
                        )
                    }
                },
                modifier = Modifier
                    .weight(0.35f)
                    .height(45.dp),
//                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage)),
                shape = RoundedCornerShape(30.dp),
            ) {
                Text(
                    text = "Money",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = cooperRegular,
                    fontSize = 24.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Button(
                onClick = {
                    expenseRecords.value = expenseRecorViewModel.averagePurpose(
                        expenseRecords.value,
                        purpose.value
                    )
                    Log.d(
                        "66666666", expenseRecorViewModel.averagePurposeIncome(
                            expenseRecorViewModel.getInc(expenseRecords.value)
                        ).toString()
                    )
                    Log.d(
                        "66666666", expenseRecorViewModel.averagePurposeExpenses(
                            expenseRecorViewModel.getExp(expenseRecords.value)
                        ).toString()
                    )
                },
                modifier = Modifier
                    .weight(0.35f)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage)),
                shape = RoundedCornerShape(30.dp),
            ) {
                Text(
                    text = "Purpose",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = cooperRegular,
                    fontSize = 24.sp

                )
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Button(
                onClick = {
                    expenseRecords.value = expenseRecorViewModel.expenseRecorState.value.list.copy()
//                val toast = Toast.makeText(context, "This is a toast message", Toast.LENGTH_SHORT)
//                toast.show()

                },
                modifier = Modifier
                    .weight(0.35f)
                    .height(45.dp),
//                    .padding(start = 15.dp, end = 15.dp, top = 10.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage)),
                shape = RoundedCornerShape(30.dp),
            ) {
                Text(
                    text = "Reset",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = cooperRegular,
                    fontSize = 24.sp,
//                    TextStyle =
                )
            }
        }
    }

}

@Composable
fun DialogView(
    content: String,
    showDiaLog: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            showDiaLog()
        },
//        modifier = Modifier.background(),
        confirmButton = {
            TextButton(
                onClick = {
                    showDiaLog()
                }
            ) {
                androidx.compose.material.Text(
                    "Close", fontFamily = cooperRegular,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        },
        title = {
            androidx.compose.material.Text(
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
                fontSize = 20.sp,
                color = colorResource(id = R.color.boderimage)
            )
        },
    )
}


private fun formatDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}


private fun convertMillisecondsToDate(milliseconds: Long): Date {
    return Date(milliseconds)
}

private fun validateDate(
    dateFrom: Date,
    dateTo: Date,
    setContent: (String) -> Unit
): Boolean {
    return if (dateFrom.time <= dateTo.time) {
        false
    } else {
        setContent("Date to must bigger than date from")
        true
    }
}

private fun validateMoney(
    moneyFrom: Double,
    moneyTo: Double,
    setContent: (String) -> Unit
): Boolean {
    return if (moneyFrom >= moneyTo) {
        setContent("Money to must bigger than money from")
        false
    } else
        true
}