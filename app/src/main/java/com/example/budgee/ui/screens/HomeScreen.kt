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
import com.example.budgee.R
import com.example.budgee.ui.components.AnimatedAmountText
import com.example.budgee.ui.components.BudgetActionButton
import com.example.budgee.ui.components.BudgetProgressRing
import com.example.budgee.ui.components.HomeTopBar
import com.example.budgee.ui.components.TransactionRow
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var transactions by remember { mutableStateOf(mockTransactions()) }
    val monthlyBudget = 500.00
    val balance = monthlyBudget - transactions.filter { !it.isIncome }.sumOf { it.amount } +
            transactions.filter { it.isIncome }.sumOf { it.amount }
    val usedFraction = ((monthlyBudget - balance) / monthlyBudget).toFloat().coerceIn(0f, 1f)

    // TODO: periodLabel/periodRange are hardcoded mock values for now.
    // Replace with real values derived from the reset-day setting once
    // SettingsViewModel/BudgetRepository are wired up.
    val periodLabel = "Αύγουστος 2026"
    val periodRange = "21 Αυγ – 20 Σεπ"

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HomeTopBar(
            periodLabel = periodLabel,
            periodRange = periodRange,
            onSettingsClick = {}
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
                    AnimatedAmountText(amount = balance)
                    Spacer(modifier = Modifier.height(24.dp))
                    BudgetProgressRing(usedFraction = usedFraction)
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        BudgetActionButton(
                            label = stringResource(R.string.action_income),
                            isIncome = true,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        BudgetActionButton(
                            label = stringResource(R.string.action_expense),
                            isIncome = false,
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            items(transactions, key = { it.id }) { transaction ->
                TransactionRow(
                    reason = transaction.reason,
                    dateLabel = transaction.dateLabel,
                    amount = transaction.amount,
                    isIncome = transaction.isIncome,
                    onDelete = {
                        transactions = transactions.filter { it.id != transaction.id }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenPreview() {
    BudgeeTheme {
        HomeScreen()
    }
}