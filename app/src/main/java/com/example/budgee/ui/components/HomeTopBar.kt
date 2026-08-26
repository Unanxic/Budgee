package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary

@Composable
fun HomeTopBar(
    periodLabel: String,
    periodRange: String,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = periodLabel,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = periodRange,
                color = TextSecondary,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        PillButton(
            text = stringResource(R.string.settings_button_label),
            onClick = onSettingsClick
        )
    }
}

@Preview(name = "HomeTopBar", showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390)
@Composable
private fun HomeTopBarPreview() {
    BudgeeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF100B1C))
        ) {
            HomeTopBar(
                periodLabel = "Αύγουστος 2026",
                periodRange = "21 Αυγ – 20 Σεπ",
                onSettingsClick = {}
            )
        }
    }
}