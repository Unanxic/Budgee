package com.example.budgee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgee.R
import com.example.budgee.domain.model.Transaction
import com.example.budgee.presentation.home.HomeUiState
import com.example.budgee.presentation.home.HomeViewModel
import com.example.budgee.ui.components.AddTransactionSheet
import com.example.budgee.ui.components.AnimatedAmountText
import com.example.budgee.ui.components.BudgetActionButton
import com.example.budgee.ui.components.BudgetProgressRing
import com.example.budgee.ui.components.EmptyStateView
import com.example.budgee.ui.components.HomeTopBar
import com.example.budgee.ui.components.LoadingView
import com.example.budgee.ui.components.SettingsSheet
import com.example.budgee.ui.components.TransactionRow
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.utils.appendDecimal
import com.example.budgee.utils.appendDigit
import com.example.budgee.utils.backspaceAmount
import com.example.budgee.utils.toAmountDouble
import com.example.budgee.utils.toShortDateLabel

/**
 * Stateful entry point: reads [HomeViewModel] via Hilt and delegates
 * rendering to [HomeScreenContent]. Kept separate from the stateless
 * content so previews can supply a fixed [HomeUiState] without Hilt.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onAddTransaction = viewModel::addTransaction,
        onDeleteTransaction = viewModel::deleteTransaction,
        onUpdateSettings = viewModel::updateSettings,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onAddTransaction: (reason: String, amount: Double, isIncome: Boolean) -> Unit,
    onDeleteTransaction: (id: Long) -> Unit,
    onUpdateSettings: (monthlyBudget: Double, resetDay: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSettingsSheet by remember { mutableStateOf(false) }
    var settingsAmountText by remember { mutableStateOf("") }
    var settingsResetDay by remember { mutableStateOf(1) }

    var showAddTransactionSheet by remember { mutableStateOf(false) }
    var addTransactionIsIncome by remember { mutableStateOf(true) }
    var addTransactionAmountText by remember { mutableStateOf("") }
    var addTransactionReason by remember { mutableStateOf("") }

    when (uiState) {
        is HomeUiState.Loading -> {
            LoadingView(modifier = modifier.fillMaxSize())
        }

        is HomeUiState.Empty -> {
            Column(modifier = modifier.fillMaxSize()) {
                HomeTopBar(
                    periodLabel = "",
                    periodRange = "",
                    onSettingsClick = {
                        settingsAmountText = ""
                        settingsResetDay = 1
                        showSettingsSheet = true
                    }
                )
                EmptyStateView(
                    modifier = Modifier.fillMaxSize(),
                    title = stringResource(R.string.home_empty_title),
                    subtitle = stringResource(R.string.home_empty_subtitle),
                    actionLabel = stringResource(R.string.home_empty_cta),
                    onActionClick = {
                        settingsAmountText = ""
                        settingsResetDay = 1
                        showSettingsSheet = true
                    }
                )
            }
        }

        is HomeUiState.Content -> {
            Column(modifier = modifier.fillMaxSize()) {
                HomeTopBar(
                    periodLabel = uiState.periodLabel,
                    periodRange = uiState.periodRange,
                    onSettingsClick = {
                        settingsAmountText = uiState.monthlyBudget.toInt().toString()
                        settingsResetDay = uiState.resetDay
                        showSettingsSheet = true
                    }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.home_balance_label),
                                style = MaterialTheme.typography.labelMedium,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            AnimatedAmountText(amount = uiState.balance)
                            Spacer(modifier = Modifier.height(24.dp))
                            BudgetProgressRing(usedFraction = uiState.usedFraction)
                            Spacer(modifier = Modifier.height(24.dp))

                            Row(modifier = Modifier.fillMaxWidth()) {
                                BudgetActionButton(
                                    label = stringResource(R.string.action_income),
                                    isIncome = true,
                                    onClick = {
                                        addTransactionIsIncome = true
                                        addTransactionAmountText = ""
                                        addTransactionReason = ""
                                        showAddTransactionSheet = true
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                BudgetActionButton(
                                    label = stringResource(R.string.action_expense),
                                    isIncome = false,
                                    onClick = {
                                        addTransactionIsIncome = false
                                        addTransactionAmountText = ""
                                        addTransactionReason = ""
                                        showAddTransactionSheet = true
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }

                    items(uiState.transactions, key = { it.id }) { transaction ->
                        TransactionRow(
                            reason = transaction.reason,
                            dateLabel = transaction.timestampMillis.toShortDateLabel(),
                            amount = transaction.amount,
                            isIncome = transaction.isIncome,
                            onDelete = { onDeleteTransaction(transaction.id) }
                        )
                    }
                }
            }
        }
    }

    if (showSettingsSheet) {
        SettingsSheet(
            monthlyAmount = settingsAmountText,
            onMonthlyAmountChange = { settingsAmountText = it },
            resetDay = settingsResetDay,
            onResetDayChange = { settingsResetDay = it },
            onSave = {
                val amount = settingsAmountText.toDoubleOrNull() ?: 0.0
                onUpdateSettings(amount, settingsResetDay)
                showSettingsSheet = false
            },
            onDismissRequest = { showSettingsSheet = false }
        )
    }

    if (showAddTransactionSheet) {
        AddTransactionSheet(
            isIncome = addTransactionIsIncome,
            onIsIncomeChange = { addTransactionIsIncome = it },
            amountText = addTransactionAmountText,
            onDigitPress = { digit ->
                addTransactionAmountText = addTransactionAmountText.appendDigit(digit)
            },
            onDecimalPress = {
                addTransactionAmountText = addTransactionAmountText.appendDecimal()
            },
            onBackspacePress = {
                addTransactionAmountText = addTransactionAmountText.backspaceAmount()
            },
            reason = addTransactionReason,
            onReasonChange = { addTransactionReason = it },
            onConfirm = {
                val amount = addTransactionAmountText.toAmountDouble()
                if (amount > 0.0 && addTransactionReason.isNotBlank()) {
                    onAddTransaction(addTransactionReason, amount, addTransactionIsIncome)
                    showAddTransactionSheet = false
                }
            },
            onDismissRequest = { showAddTransactionSheet = false }
        )
    }
}

@Preview(name = "Home - Content", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenContentPreview() {
    BudgeeTheme {
        HomeScreenContent(
            uiState = HomeUiState.Content(
                periodLabel = "Αύγουστος 2026",
                periodRange = "21 Αυγ – 20 Σεπ",
                balance = 373.00,
                monthlyBudget = 500.00,
                resetDay = 21,
                usedFraction = 0.25f,
                transactions = listOf(
                    Transaction(1, "Κινηματογράφος", 14.50, false, System.currentTimeMillis()),
                    Transaction(2, "Μισθός (bonus)", 120.00, true, System.currentTimeMillis())
                )
            ),
            onAddTransaction = { _, _, _ -> },
            onDeleteTransaction = {},
            onUpdateSettings = { _, _ -> }
        )
    }
}

@Preview(name = "Home - Empty", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenEmptyPreview() {
    BudgeeTheme {
        HomeScreenContent(
            uiState = HomeUiState.Empty,
            onAddTransaction = { _, _, _ -> },
            onDeleteTransaction = {},
            onUpdateSettings = { _, _ -> }
        )
    }
}

@Preview(name = "Home - Loading", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenLoadingPreview() {
    BudgeeTheme {
        HomeScreenContent(
            uiState = HomeUiState.Loading,
            onAddTransaction = { _, _, _ -> },
            onDeleteTransaction = {},
            onUpdateSettings = { _, _ -> }
        )
    }
}