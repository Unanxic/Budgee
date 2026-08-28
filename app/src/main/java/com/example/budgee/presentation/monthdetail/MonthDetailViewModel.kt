package com.example.budgee.presentation.monthdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgee.domain.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MonthDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: BudgetRepository
) : ViewModel() {

    private val monthId: Long = checkNotNull(savedStateHandle["monthId"])

    val uiState: StateFlow<MonthDetailUiState> = repository.observeArchivedMonths()
        .map { months -> months.find { it.id == monthId } }
        .filterNotNull()
        .map { month -> MonthDetailUiState.Content(month) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = MonthDetailUiState.Loading
        )
}