package com.example.budgee.domain.model

data class Transaction(
    val id: Long = 0,
    val reason: String,
    val amount: Double,
    val isIncome: Boolean,
    val timestampMillis: Long
)