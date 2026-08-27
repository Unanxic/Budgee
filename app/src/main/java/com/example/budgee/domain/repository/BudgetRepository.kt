package com.example.budgee.domain.repository

import com.example.budgee.domain.model.ArchivedMonth
import com.example.budgee.domain.model.MonthlySettings
import com.example.budgee.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {

    fun observeCurrentMonthTransactions(): Flow<List<Transaction>>

    fun observeSettings(): Flow<MonthlySettings>

    fun observeArchivedMonths(): Flow<List<ArchivedMonth>>

    suspend fun addTransaction(reason: String, amount: Double, isIncome: Boolean)

    suspend fun deleteTransaction(id: Long)

    suspend fun updateSettings(monthlyBudget: Double, resetDay: Int)
}