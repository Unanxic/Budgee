package com.example.budgee.ui.screens

data class ArchivedMonth(
    val id: Long,
    val monthLabel: String,
    val transactionCount: Int,
    val startingBudget: Double,
    val closingBalance: Double,
    val transactions: List<Transaction>
)

fun mockArchivedMonths(): List<ArchivedMonth> = listOf(
    ArchivedMonth(
        id = 1,
        monthLabel = "Ιούλιος 2026",
        transactionCount = 6,
        startingBudget = 500.00,
        closingBalance = 87.40,
        transactions = listOf(
            Transaction(101, "Ρούχα", "18 Αυγ", 130.70, isIncome = false),
            Transaction(102, "Εστιατόριο", "11 Αυγ", 58.30, isIncome = false),
            Transaction(103, "Λογαριασμός ΕΥΔΑΠ", "3 Αυγ", 41.20, isIncome = false),
            Transaction(104, "Επιστροφή δανείου", "28 Ιουλ", 60.00, isIncome = true),
            Transaction(105, "Σούπερ μάρκετ", "23 Ιουλ", 92.40, isIncome = false),
            Transaction(106, "Ενοίκιο (μερικό)", "21 Ιουλ", 150.00, isIncome = false)
        )
    ),
    ArchivedMonth(
        id = 2,
        monthLabel = "Ιούνιος 2026",
        transactionCount = 5,
        startingBudget = 500.00,
        closingBalance = 326.15,
        transactions = emptyList()
    ),
    ArchivedMonth(
        id = 3,
        monthLabel = "Μάιος 2026",
        transactionCount = 5,
        startingBudget = 450.00,
        closingBalance = 170.50,
        transactions = emptyList()
    )
)