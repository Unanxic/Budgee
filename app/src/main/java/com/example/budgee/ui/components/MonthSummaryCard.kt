package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.theme.Amber
import com.example.budgee.ui.theme.ArchivedRowBackground
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.Mint
import com.example.budgee.ui.theme.Rose
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.utils.pulsateClick
import com.example.budgee.utils.toEuroString

@Composable
fun MonthSummaryCard(
    monthLabel: String,
    transactionCount: Int,
    startingBudget: Double,
    closingBalance: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spentFraction = ((startingBudget - closingBalance) / startingBudget)
        .toFloat()
        .coerceIn(0f, 1f)
    val barColor = when {
        spentFraction >= 0.9f -> Rose
        spentFraction >= 0.6f -> Amber
        else -> Mint
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .pulsateClick(onClick)
            .background(ArchivedRowBackground, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = monthLabel,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = pluralStringResource(
                    R.plurals.history_transactions_count,
                    transactionCount,
                    transactionCount
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = stringResource(R.string.history_set_label),
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
                Text(
                    text = startingBudget.toEuroString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.history_closed_with_label),
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
                Text(
                    text = closingBalance.toEuroString(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = barColor
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(TextSecondary.copy(alpha = 0.15f), RoundedCornerShape(3.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(spentFraction)
                    .height(6.dp)
                    .background(barColor, RoundedCornerShape(3.dp))
            ) {}
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun MonthSummaryCardPreview() {
    BudgeeTheme {
        Column {
            MonthSummaryCard(
                monthLabel = "Ιούλιος 2026",
                transactionCount = 6,
                startingBudget = 500.00,
                closingBalance = 87.40,
                onClick = {}
            )
            MonthSummaryCard(
                monthLabel = "Ιούνιος 2026",
                transactionCount = 5,
                startingBudget = 500.00,
                closingBalance = 326.15,
                onClick = {},
                modifier = Modifier.padding(top = 10.dp)
            )
            MonthSummaryCard(
                monthLabel = "Αύγουστος 2026",
                transactionCount = 7,
                startingBudget = 500.00,
                closingBalance = 0.00,
                onClick = {},
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}