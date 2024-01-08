package com.example.nln.Partials

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nln.Models.ExpenseRecordFireBases
import com.example.nln.R
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.ui.theme.cooperRegular

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListTransactionView(
    navController: NavController,
    expenseRecordFireBases: List<ExpenseRecordFireBases>,
    expenseRecorViewModel: ExpenseRecorViewModel,
) {
    val colorArray = arrayOf(R.color.item1, R.color.item2, R.color.item3, R.color.item4)
    var colorResourceId: Int = 0

    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
    ) {
        items(
            expenseRecordFireBases,
            key = { expenseRecordFireBase -> expenseRecordFireBase.id })
        { expenseRecordFireBase ->
            TransactionDetailView(
                navController,
                expenseRecorViewModel,
                expenseRecordFireBase,
                colorArray[colorResourceId],
            )
            if (colorResourceId == colorArray.size - 1) {
                colorResourceId = 0
            } else colorResourceId++
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
suspend fun resetDismiss(dismissState: DismissState) {
    dismissState.reset()
}
