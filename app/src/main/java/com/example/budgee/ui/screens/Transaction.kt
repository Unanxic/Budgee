package com.example.budgee.ui.screens

data class Transaction(
    val id: Long,
    val reason: String,
    val dateLabel: String,
    val amount: Double,
    val isIncome: Boolean
)

fun mockTransactions(): List<Transaction> = listOf(
    Transaction(1, "Κινηματογράφος", "26 Αυγ", 14.50, isIncome = false),
    Transaction(2, "Φαρμακείο", "25 Αυγ", 18.25, isIncome = false),
    Transaction(3, "Καφές & φαγητό", "24 Αυγ", 24.70, isIncome = false),
    Transaction(4, "Βενζίνη", "23 Αυγ", 40.00, isIncome = false),
    Transaction(5, "Σούπερ μάρκετ", "22 Αυγ", 63.15, isIncome = false),
    Transaction(6, "Λογαριασμός ΔΕΗ", "21 Αυγ", 86.40, isIncome = false),
    Transaction(7, "Μισθός (bonus)", "21 Αυγ", 120.00, isIncome = true)
)