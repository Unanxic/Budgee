package com.example.budgee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgee.R
import com.example.budgee.domain.model.ArchivedMonth
import com.example.budgee.domain.model.Transaction
import com.example.budgee.presentation.monthdetail.MonthDetailUiState
import com.example.budgee.presentation.monthdetail.MonthDetailViewModel
import com.example.budgee.ui.components.LoadingView
import com.example.budgee.ui.components.PillButton
import com.example.budgee.ui.components.TransactionRow
import com.example.budgee.ui.theme.AppBackground
import com.example.budgee.ui.theme.ArchivedRowBackground
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.utils.toEuroString
import com.example.budgee.utils.toShortDateLabel

/**
 * Stateful entry point: reads [MonthDetailViewModel] via Hilt (which
 * reads monthId from SavedStateHandle) and delegates rendering to
 * [MonthDetailScreenContent], so previews can supply a fixed
 * [MonthDetailUiState] without Hilt/Navigation.
 */
@Composable
fun MonthDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MonthDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MonthDetailScreenContent(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun MonthDetailScreenContent(
    uiState: MonthDetailUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
    ) {
        when (uiState) {
            is MonthDetailUiState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            is MonthDetailUiState.Content -> {
                val month = uiState.month

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PillButton(
                        text = stringResource(R.string.month_detail_back_label),
                        onClick = onBackClick
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = month.monthLabel,
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SummaryChip(
                                label = stringResource(R.string.month_detail_set_label),
                                amount = month.startingBudget,
                                color = TextPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            SummaryChip(
                                label = stringResource(R.string.month_detail_final_balance_label),
                                amount = month.closingBalance,
                                color = TextPrimary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = stringResource(R.string.month_detail_readonly_label),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }

                    items(month.transactions, key = { it.id }) { transaction ->
                        TransactionRow(
                            reason = transaction.reason,
                            dateLabel = transaction.timestampMillis.toShortDateLabel(),
                            amount = transaction.amount,
                            isIncome = transaction.isIncome
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryChip(
    label: String,
    amount: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(ArchivedRowBackground, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary
        )
        Text(
            text = amount.toEuroString(),
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}

@Preview(name = "MonthDetail - Content", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun MonthDetailScreenContentPreview() {
    BudgeeTheme {
        MonthDetailScreenContent(
            uiState = MonthDetailUiState.Content(
                month = ArchivedMonth(
                    id = 1,
                    monthLabel = "Ιούλιος 2026",
                    startingBudget = 500.00,
                    closingBalance = 87.40,
                    transactions = listOf(
                        Transaction(101, "Ρούχα", 130.70, false, System.currentTimeMillis()),
                        Transaction(102, "Επιστροφή δανείου", 60.00, true, System.currentTimeMillis())
                    )
                )
            ),
            onBackClick = {}
        )
    }
}

@Preview(name = "MonthDetail - Loading", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun MonthDetailScreenLoadingPreview() {
    BudgeeTheme {
        MonthDetailScreenContent(
            uiState = MonthDetailUiState.Loading,
            onBackClick = {}
        )
    }
}