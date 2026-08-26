package com.example.budgee.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextSecondary

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ιστορικό — σύντομα",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun HistoryScreenPreview() {
    BudgeeTheme {
        HistoryScreen()
    }
}