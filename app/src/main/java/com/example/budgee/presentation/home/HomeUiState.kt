package com.example.budgee.presentation.home

import com.example.budgee.domain.model.Transaction

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data object Empty : HomeUiState

    data class Content(
        val periodLabel: String,
        val periodRange: String,
        val balance: Double,
        val monthlyBudget: Double,
        val resetDay: Int,
        val usedFraction: Float,
        val transactions: List<Transaction>
    ) : HomeUiState
}