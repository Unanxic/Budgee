package com.example.budgee.domain.model

data class ArchivedMonth(
    val id: Long = 0,
    val monthLabel: String,
    val startingBudget: Double,
    val closingBalance: Double,
    val transactions: List<Transaction>
) {
    val transactionCount: Int
        get() = transactions.size
}