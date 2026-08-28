package com.example.budgee.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgee.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    repository: BudgetRepository
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> = repository.observeArchivedMonths()
        .map { archivedMonths ->
            if (archivedMonths.isEmpty()) {
                HistoryUiState.Empty
            } else {
                HistoryUiState.Content(archivedMonths)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = HistoryUiState.Loading
        )
}