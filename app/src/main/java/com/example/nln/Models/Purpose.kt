package com.example.nln.Models

data class Purpose(val name: String, var quantity: Int)

val purposes = listOf(
    Purpose("Salary", 0),
    Purpose("Freelance", 0),
    Purpose("Business", 0),
    Purpose("Bonuses", 0),
    Purpose("Different", 0),
    Purpose("Electricity Bill", 0),
    Purpose("Rent", 0),
    Purpose("Grocery", 0),
    Purpose("Transportation", 0),
    Purpose("Entertainment", 0),
    Purpose("Insurance", 0),
    Purpose("Vacation", 0),
    Purpose("Different", 0),
)

val purposesIncome = mapOf(
    "Salary" to Purpose("Salary", 0),
    "Freelance" to Purpose("Freelance", 0),
    "Business" to Purpose("Business", 0),
    "Bonuses" to Purpose("Bonuses", 0),
    "Different" to Purpose("Different", 0)
)

val purposesExpenses = mapOf(
    "Electricity Bill" to Purpose("Electricity Bill", 0),
    "Rent" to Purpose("Rent", 0),
    "Grocery" to Purpose("Grocery", 0),
    "Transportation" to Purpose("Transportation", 0),
    "Entertainment" to Purpose("Entertainment", 0),
    "Insurance" to Purpose("Insurance", 0),
    "Vacation" to Purpose("Vacation", 0),
    "Different" to Purpose("Different", 0)
)