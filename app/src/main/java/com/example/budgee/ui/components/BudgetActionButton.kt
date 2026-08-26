package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.budgee.utils.pulsateClick
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.Canvas
import com.example.budgee.ui.theme.Mint
import com.example.budgee.ui.theme.Rose

@Composable
fun BudgetActionButton(
    label: String,
    isIncome: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isIncome) Mint else Rose.copy(alpha = 0.16f)
    val contentColor = if (isIncome) Canvas else Rose

    Row(
        modifier = modifier
            .fillMaxWidth()
            .pulsateClick(onClick)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = if (isIncome) R.drawable.ic_plus else R.drawable.ic_minus
            ),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = contentColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 360)
@Composable
private fun BudgetActionButtonPreview() {
    BudgeeTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            BudgetActionButton(
                label = "Έσοδα",
                isIncome = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            BudgetActionButton(
                label = "Έξοδα",
                isIncome = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}