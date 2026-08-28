package com.example.budgee.presentation.monthdetail

import com.example.budgee.domain.model.ArchivedMonth

sealed interface MonthDetailUiState {

    data object Loading : MonthDetailUiState

    data class Content(
        val month: ArchivedMonth
    ) : MonthDetailUiState
}