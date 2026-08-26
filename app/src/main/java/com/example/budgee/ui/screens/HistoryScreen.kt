package com.example.budgee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.components.MonthSummaryCard
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary

@Composable
fun HistoryScreen(
    onMonthClick: (ArchivedMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    val archivedMonths = mockArchivedMonths()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Column {
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

            items(archivedMonths, key = { it.id }) { archivedMonth ->
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

@Preview(showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HistoryScreenPreview() {
    BudgeeTheme {
        HistoryScreen(onMonthClick = {})
    }
}