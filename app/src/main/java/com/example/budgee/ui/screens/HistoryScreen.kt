package com.example.budgee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgee.R
import com.example.budgee.domain.model.ArchivedMonth
import com.example.budgee.presentation.history.HistoryUiState
import com.example.budgee.presentation.history.HistoryViewModel
import com.example.budgee.ui.components.EmptyStateView
import com.example.budgee.ui.components.LoadingView
import com.example.budgee.ui.components.MonthSummaryCard
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary

/**
 * Stateful entry point: reads [HistoryViewModel] via Hilt and delegates
 * rendering to [HistoryScreenContent], so previews can supply a fixed
 * [HistoryUiState] without Hilt.
 */
@Composable
fun HistoryScreen(
    onMonthClick: (ArchivedMonth) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryScreenContent(
        uiState = uiState,
        onMonthClick = onMonthClick,
        modifier = modifier
    )
}

@Composable
private fun HistoryScreenContent(
    uiState: HistoryUiState,
    onMonthClick: (ArchivedMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is HistoryUiState.Loading -> {
            LoadingView(modifier = modifier.fillMaxSize())
        }

        is HistoryUiState.Empty -> {
            Column(modifier = modifier.fillMaxSize()) {
                HistoryHeader(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp))
                EmptyStateView(
                    modifier = Modifier.fillMaxSize(),
                    title = stringResource(R.string.history_empty_title),
                    subtitle = stringResource(R.string.history_empty_subtitle)
                )
            }
        }

        is HistoryUiState.Content -> {
            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { HistoryHeader() }

                    items(uiState.archivedMonths, key = { it.id }) { archivedMonth ->
                        MonthSummaryCard(
                            monthLabel = archivedMonth.monthLabel,
                            transactionCount = archivedMonth.transactionCount,
                            startingBudget = archivedMonth.startingBudget,
                            closingBalance = archivedMonth.closingBalance,
                            onClick = { onMonthClick(archivedMonth) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryHeader(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.history_title),
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary
        )
        Text(
            text = stringResource(R.string.history_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "History - Content", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HistoryScreenContentPreview() {
    BudgeeTheme {
        HistoryScreenContent(
            uiState = HistoryUiState.Content(
                archivedMonths = listOf(
                    ArchivedMonth(1, "Ιούλιος 2026", 500.00, 87.40, emptyList()),
                    ArchivedMonth(2, "Ιούνιος 2026", 500.00, 326.15, emptyList())
                )
            ),
            onMonthClick = {}
        )
    }
}

@Preview(name = "History - Empty", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HistoryScreenEmptyPreview() {
    BudgeeTheme {
        HistoryScreenContent(
            uiState = HistoryUiState.Empty,
            onMonthClick = {}
        )
    }
}

@Preview(name = "History - Loading", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HistoryScreenLoadingPreview() {
    BudgeeTheme {
        HistoryScreenContent(
            uiState = HistoryUiState.Loading,
            onMonthClick = {}
        )
    }
}