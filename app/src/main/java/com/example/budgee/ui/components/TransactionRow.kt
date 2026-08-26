package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.CardBackground
import com.example.budgee.ui.theme.Mint
import com.example.budgee.ui.theme.Rose
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.utils.toSignedEuroString

/**
 * A single transaction row: income/expense icon, reason, date, amount.
 * Used in the "Recent transactions" list (Home) and in the detailed
 * archived-month history (History).
 */
@Composable
fun TransactionRow(
    reason: String,
    dateLabel: String,
    amount: Double,
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {
    val accentColor = if (isIncome) Mint else Rose

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CardBackground, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular income/expense icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(accentColor.copy(alpha = 0.18f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = if (isIncome) R.drawable.ic_plus else R.drawable.ic_minus
                ),
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = reason,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = dateLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        Text(
            text = amount.toSignedEuroString(isIncome),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = accentColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun TransactionRowPreview() {
    BudgeeTheme {
        Column {
            TransactionRow(
                reason = "Κινηματογράφος",
                dateLabel = "26 Αυγ",
                amount = 14.50,
                isIncome = false
            )
            TransactionRow(
                reason = "Μισθός (bonus)",
                dateLabel = "21 Αυγ",
                amount = 120.00,
                isIncome = true,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}