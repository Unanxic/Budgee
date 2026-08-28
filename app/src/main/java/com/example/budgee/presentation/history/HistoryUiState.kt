package com.example.budgee.presentation.history

import com.example.budgee.domain.model.ArchivedMonth

sealed interface HistoryUiState {

    data object Loading : HistoryUiState

    data object Empty : HistoryUiState

    data class Content(
        val archivedMonths: List<ArchivedMonth>
    ) : HistoryUiState
}