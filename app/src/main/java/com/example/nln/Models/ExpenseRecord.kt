package com.example.nln.Models

import java.util.Date

data class ExpenseRecord(
    val isIncome: Boolean,
    val purpose: String,
    val date: Long,
    val description: String,
    val money: Double
)

data class ExpenseRecordFireBases(val expenseRecord: ExpenseRecord, val id: String)

data class ExpenseRecords(val expenseRecords: List<ExpenseRecordFireBases>)


