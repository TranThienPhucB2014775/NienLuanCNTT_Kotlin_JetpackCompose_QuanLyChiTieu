package com.example.nln.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nln.Models.ExpenseRecord
import com.example.nln.Models.ExpenseRecordFireBases
import com.example.nln.Models.ExpenseRecords
import com.example.nln.Models.Purpose
import com.example.nln.Models.purposesExpenses
import com.example.nln.Models.purposesIncome
import com.example.nln.Services.firebaseExpenseRecordService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseRecorViewModel(val token: String) : ViewModel() {

    private val _expenseRecorState = mutableStateOf(RecipeState())

    val expenseRecorState: State<RecipeState> = _expenseRecorState

    init {
        fetchExpenseRecors()
    }

    fun fetch() {
        fetchExpenseRecors()
    }

    suspend fun addExpenseRecor(expenseRecord: ExpenseRecord) {
        _expenseRecorState.value = _expenseRecorState.value.copy(
            loading = true,
            list = ExpenseRecords(emptyList()),
            error = null
        )
        viewModelScope.launch {
            try {
                val expenseRecordResponse = firebaseExpenseRecordService.addExpenseRecor(
                    token,
                    token,
                    expenseRecord
                )
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(emptyList()),
                    error = null
                )
            } catch (e: Exception) {
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(emptyList()),
                    error = "Error, please try against"
                )
            }
        }
    }

    suspend fun updateExpenseRecor(expenseRecord: ExpenseRecord, id: String) {
        _expenseRecorState.value = _expenseRecorState.value.copy(
            loading = true,
            list = ExpenseRecords(emptyList()),
            error = null
        )
        viewModelScope.launch {
            try {
                val expenseRecordResponse = firebaseExpenseRecordService.updateExpenseRecord(
                    token,
                    id,
                    token,
                    expenseRecord
                )
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(emptyList()),
                    error = null
                )
            } catch (e: Exception) {
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(emptyList()),
                    error = "Error, please try against"
                )
            }
        }
    }

    private fun fetchExpenseRecors() {
        viewModelScope.launch {
            try {
                val expenseRecordResponse = firebaseExpenseRecordService.getAllExpenseRecord(
                    token,
                    token,
                )

                val gson = Gson()
                val type = object : TypeToken<Map<String, Any>>() {}.type
                val data: Map<String, Any> =
                    gson.fromJson(expenseRecordResponse.string().trimIndent(), type)
                val expenseRecordFireBases = data.map { (id, recordData) ->
                    val expenseRecord =
                        gson.fromJson(gson.toJson(recordData), ExpenseRecord::class.java)
                    ExpenseRecordFireBases(expenseRecord, id)
                }
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(expenseRecordFireBases),
                    error = null
                )
            } catch (e: Exception) {
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(emptyList()),
                    error = "Error, please try against"
                )
            }
        }
    }

    fun deleteExpenseRecors(id: String) {
        _expenseRecorState.value = _expenseRecorState.value.copy(
            loading = true,
            list = _expenseRecorState.value.list,
            error = null
        )
        viewModelScope.launch {
            try {
                val expenseRecordResponse = firebaseExpenseRecordService.deleteExpenseRecord(
                    token,
                    id,
                    token,
                )
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(_expenseRecorState.value.list.expenseRecords.filter { it.id != id }),
                    error = null
                )
//                deleteExpenseRecorsLocal(id)
            } catch (e: Exception) {
                _expenseRecorState.value = _expenseRecorState.value.copy(
                    loading = false,
                    list = ExpenseRecords(emptyList()),
                    error = "Error, please try against"
                )
            }
        }
    }

    fun averagePurposeIncome(expenseRecords: ExpenseRecords): Map<String, Purpose> {
        val purposeCounts = purposesIncome.toMap()

        purposeCounts.forEach {
//            val purpose = it..purpose
            purposeCounts[it.value.name]?.quantity = 0
        }

        expenseRecords.expenseRecords.forEach {
            val purpose = it.expenseRecord.purpose
            purposeCounts[purpose]?.quantity = purposeCounts[purpose]?.quantity!! + 1
        }
        return purposeCounts
    }

    fun averagePurposeExpenses(expenseRecords: ExpenseRecords): Map<String, Purpose> {
        val purposeCounts = purposesExpenses.toMap()

        purposeCounts.forEach {
//            val purpose = it..purpose
            purposeCounts[it.value.name]?.quantity = 0
        }

        Log.d("6666667",purposeCounts.toString())
        expenseRecords.expenseRecords.forEach {
            val purpose = it.expenseRecord.purpose
            purposeCounts[purpose]?.quantity = purposeCounts[purpose]?.quantity!! + 1
        }
        return purposeCounts
    }

    fun averageDate(
        expenseRecords: ExpenseRecords,
        dateFrom: Long,
        dateTo: Long,
    ): ExpenseRecords {
        var expenseRecordsFilter = expenseRecords.expenseRecords.toList()
        expenseRecordsFilter = expenseRecordsFilter.filter {
            it.expenseRecord.date in (dateFrom)..dateTo
        }
        return ExpenseRecords(expenseRecordsFilter)
    }

    fun averageMoney(
        expenseRecords: ExpenseRecords,
        moneyFrom: Double,
        moneyTo: Double
    ): ExpenseRecords {
        var expenseRecordsFilter = expenseRecords.expenseRecords.toList()
        expenseRecordsFilter = expenseRecordsFilter.filter {
            it.expenseRecord.money in moneyFrom..moneyTo
        }
        return ExpenseRecords(expenseRecordsFilter)
    }

    fun averagePurpose(
        expenseRecords: ExpenseRecords,
        purpose: String
    ): ExpenseRecords {
        if (
            purpose != "All"
        ) {
            var expenseRecordsFilter = expenseRecords.expenseRecords.toList()
            expenseRecordsFilter = expenseRecordsFilter.filter {
                it.expenseRecord.purpose == purpose
            }
            return ExpenseRecords(expenseRecordsFilter)
        } else return expenseRecords
    }

    fun getInc(expenseRecords: ExpenseRecords): ExpenseRecords {
        return ExpenseRecords(expenseRecords.expenseRecords.filter {
            it.expenseRecord.isIncome // Lọc các ExpenseRecord với isIncome = true
        })

    }

    fun getExp(expenseRecords: ExpenseRecords): ExpenseRecords {
        return ExpenseRecords(expenseRecords.expenseRecords.filter {
            !it.expenseRecord.isIncome // Lọc các ExpenseRecord với isIncome = true
        })

    }

    fun countTotal(expenseRecords: ExpenseRecords): Double {
        var total = 0.0
        expenseRecords.expenseRecords.map {
            if (it.expenseRecord.isIncome) {
                total += it.expenseRecord.money
            } else {
                total -= it.expenseRecord.money
            }
        }
        return total
    }

    fun getExpenseRecorsbyId(id: String): ExpenseRecord {
        val filteredTransactionFireBases = expenseRecorState.value.list.expenseRecords.filter {
            it.id == id // Lọc các ExpenseRecord với id tương ứng
        }
        return if (filteredTransactionFireBases.isNotEmpty()) {
            filteredTransactionFireBases[0].expenseRecord
        } else {
            ExpenseRecord(true, "", Date().time, "", 0.0)
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: ExpenseRecords = ExpenseRecords(emptyList()),
        val error: String? = null
    )
}