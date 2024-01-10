package com.example.nln.AddEditIncExp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import com.example.nln.Models.ExpenseRecord
import com.example.nln.R
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.ui.theme.cooperRegular
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditDetailView(
    navController: NavController,
    authTokenViewModel: AuthTokenViewModel,
    expenseRecorViewModel: ExpenseRecorViewModel,
    type: Boolean,
    id: String,
) {
    val selected = remember {
        mutableStateOf(type)
    }
    val description = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableStateOf(Date())
    }
    val money = remember {
        mutableStateOf("0")
    }
    val comfirm = remember {
        mutableStateOf(false)
    }
    val showDate = remember {
        mutableStateOf(false)
    }
    val showType = remember {
        mutableStateOf(false)
    }
    val purpose = remember {
        mutableStateOf("Purpose")
    }
    val showDiaLog = remember {
        mutableStateOf(false)
    }
    val contentDiaLog = remember {
        mutableStateOf("")
    }
    val expenseRecords = expenseRecorViewModel.getExpenseRecorsbyId(id)
    Log.d("1`1", id)
    if (id != "empty" ) {
        if(!comfirm.value){
            selected.value = expenseRecords.isIncome
            description.value = expenseRecords.description
            date.value = Date(expenseRecords.date)
            money.value = expenseRecords.money.toString()
            purpose.value = expenseRecords.purpose
        }
    }

    val datePickerState = rememberDatePickerState()
    if (comfirm.value) {
        if (!expenseRecorViewModel.expenseRecorState.value.loading) {
            if (expenseRecorViewModel.expenseRecorState.value.error == "") {
                contentDiaLog.value = expenseRecorViewModel.expenseRecorState.value.error.toString()
                comfirm.value = false
            } else {
                contentDiaLog.value =
                    if(id == "empty"){
                        if (selected.value) "Income has been created" else "Expenses has been created"
                    }else "Update success"
                comfirm.value = false
                expenseRecorViewModel.fetch()
                if (id == "empty") {
                    comfirm.value = false
                    selected.value = type
                    description.value = ""
                    date.value = Date()
                    money.value = ""
                    purpose.value = "Purpose"
                }
            }
            showDiaLog.value = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.background)
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.Black,
                        contentDescription = "",
                    )
                }
                Text(
                    text = if (id == "0") "Add" else "Edit",
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = cooperRegular,
                )
            }
        },
        content = {
            if (showDate.value) {
                DatePickerDialog(
                    onDismissRequest = { showDate.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDate.value = false
                            if (datePickerState.selectedDateMillis != null) {
                                Log.d("123123", datePickerState.selectedDateMillis.toString())
                                date.value =
                                    convertMillisecondsToDate(datePickerState.selectedDateMillis!!)
                            }
                        }) {
                            Text("Ok")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDate.value = false }) {
                            Text("Cancel")
                        }
                    },
                ) {
                    DatePicker(
                        state = datePickerState,
                    )
                }
            }
            if (showDiaLog.value) {
                com.example.nln.AuthView.DialogView(
                    contentDiaLog.value,
                    false,
                    navController
                ) { showDiaLog.value = false }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background))
                    .padding(top = 15.dp, start = 16.dp, end = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected.value,
                            onClick = { selected.value = !selected.value },
                        )
                        Text(
                            text = "Income",
                            color = if (selected.value) colorResource(id = R.color.boderimage) else Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    selected.value = !selected.value
                                },
                            fontFamily = cooperRegular,
                        )
                    }
//                    Spacer(modifier = Modifier.width(30.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !selected.value,
                            onClick = { selected.value = !selected.value },
                        )
                        Text(
                            text = "Expenses",
                            color = if (!selected.value) colorResource(id = R.color.boderimage) else Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    selected.value = !selected.value
                                },
                            fontFamily = cooperRegular,
                        )
                    }

                }
//                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp)
                ) {
                    OutlinedTextField(
                        value = formatDateToString(date.value),
                        onValueChange = {
                            showDate.value = true
                        },
                        label = {
                            androidx.compose.material.Text(text = "Date", color = Color.Black)
                        },
                        modifier = Modifier
                            .width(190.dp)
                            .height(60.dp)
                            .padding(start = 15.dp, end = 15.dp)
                            .clickable {
                                showDate.value = true
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
                    Box {
                        OutlinedTextField(
                            value = purpose.value,
                            onValueChange = {
                                showDate.value = true
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
                            if (selected.value) {
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
                            } else {
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
                    }
                }
                OutlinedTextField(
                    value = description.value,
                    onValueChange = {
                        description.value = it
                    },
                    label = {
                        Text(text = "Description", color = Color.Black)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp),
                    shape = RoundedCornerShape(30.dp),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = cooperRegular,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = false,
                )
                OutlinedTextField(
                    value = money.value,
                    onValueChange = {
                        money.value = it
                    },
                    label = {
                        androidx.compose.material.Text(text = "Money", color = Color.Black)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp),
                    shape = RoundedCornerShape(30.dp),
                    textStyle = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = cooperRegular
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Button(
                    onClick = {
                        validateForm(
                            id,
                            expenseRecorViewModel,
                            isIncome = selected.value,
                            date = date.value,
                            purpose = purpose.value,
                            description = description.value,
                            money = money.value,
                            { showDiaLog.value = true },
                            { contentDiaLog.value = it },
                            { comfirm.value = true }
                        )
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.boderimage))

                ) {
                    Text(
                        text = "Confirm",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontFamily = cooperRegular,
                        fontSize = 24.sp

                    )
                }
            }
        }
    )
}

private fun formatDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}


private fun convertMillisecondsToDate(milliseconds: Long): Date {
    return Date(milliseconds)
}

@OptIn(DelicateCoroutinesApi::class)
private fun validateForm(
    id: String,
    expenseRecorViewModel: ExpenseRecorViewModel,
    isIncome: Boolean,
    date: Date,
    purpose: String,
    description: String,
    money: String,
    setShowDiaLog: () -> Unit,
    setContentDiaLog: (String) -> Unit,
    setComfirm: () -> Unit
) {
    if (purpose == "Purpose") {
        setContentDiaLog("Purpose can not null")
        setShowDiaLog()
        return
    } else if (description.isEmpty()
    ) {
        setContentDiaLog("Description can not null")
        setShowDiaLog()
        return
    } else if (money.isEmpty()) {
        setContentDiaLog("Money can not null")
        setShowDiaLog()
        return
    }
    GlobalScope.launch {
        if (id == "empty") {
            expenseRecorViewModel.addExpenseRecor(
                ExpenseRecord(
                    isIncome,
                    purpose,
                    date.time,
                    description,
                    money.toDouble()
                ),
            )
        } else {
            expenseRecorViewModel.updateExpenseRecor(
                ExpenseRecord(
                    isIncome,
                    purpose,
                    date.time,
                    description,
                    money.toDouble()
                ), id
            )
        }

    }
    setComfirm()
    return
}
