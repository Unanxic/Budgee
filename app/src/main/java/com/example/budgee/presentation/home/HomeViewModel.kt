package com.example.budgee.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgee.domain.model.MonthlySettings
import com.example.budgee.domain.model.Transaction
import com.example.budgee.domain.repository.BudgetRepository
import com.example.budgee.domain.util.MonthResetCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        repository.observeSettings(),
        repository.observeCurrentMonthTransactions()
    ) { settings, transactions ->
        toUiState(settings, transactions)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = HomeUiState.Loading
    )

    fun addTransaction(reason: String, amount: Double, isIncome: Boolean) {
        viewModelScope.launch {
            repository.addTransaction(reason, amount, isIncome)
        }
    }

    fun deleteTransaction(id: Long) {
        viewModelScope.launch {
            repository.deleteTransaction(id)
        }
    }

    fun updateSettings(monthlyBudget: Double, resetDay: Int) {
        viewModelScope.launch {
            repository.updateSettings(monthlyBudget, resetDay)
        }
    }

    private fun toUiState(
        settings: MonthlySettings,
        transactions: List<Transaction>
    ): HomeUiState {
        val isConfigured = settings.monthlyBudget > 0.0
        if (!isConfigured) return HomeUiState.Empty

        val expenses = transactions.filter { !it.isIncome }.sumOf { it.amount }
        val income = transactions.filter { it.isIncome }.sumOf { it.amount }
        val balance = settings.monthlyBudget - expenses + income
        val usedFraction = ((settings.monthlyBudget - balance) / settings.monthlyBudget)
            .toFloat()
            .coerceIn(0f, 1f)

        val today = LocalDate.now()
        val periodStart = MonthResetCalculator.currentPeriodStart(settings.resetDay, today)
        val periodEnd = MonthResetCalculator.currentPeriodEnd(settings.resetDay, today)
            .minusDays(1)

        return HomeUiState.Content(
            periodLabel = formatMonthLabel(periodStart),
            periodRange = formatPeriodRange(periodStart, periodEnd),
            balance = balance,
            monthlyBudget = settings.monthlyBudget,
            resetDay = settings.resetDay,
            usedFraction = usedFraction,
            transactions = transactions.sortedByDescending { it.timestampMillis }
        )
    }

    private fun formatMonthLabel(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("LLLL yyyy", Locale.forLanguageTag("el"))
        return date.format(formatter).replaceFirstChar { it.uppercase() }
    }

    private fun formatPeriodRange(start: LocalDate, end: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.forLanguageTag("el"))
        return "${start.format(formatter)} – ${end.format(formatter)}"
    }
}