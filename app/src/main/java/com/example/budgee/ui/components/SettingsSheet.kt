package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.CardBackground
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.ui.theme.Violet

private val RESET_DAYS = (1..31).toList()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSheet(
    monthlyAmount: String,
    onMonthlyAmountChange: (String) -> Unit,
    resetDay: Int,
    onResetDayChange: (Int) -> Unit,
    onSave: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = androidx.compose.material3.rememberModalBottomSheetState()
) {
    BudgeeBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState
    ) {
        Text(
            text = stringResource(R.string.settings_sheet_title),
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary
        )
        Text(
            text = stringResource(R.string.settings_sheet_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackground, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "€",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = monthlyAmount,
                onValueChange = onMonthlyAmountChange,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(Violet)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.settings_sheet_reset_day_label),
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))

        val listState = rememberLazyListState()
        LaunchedEffect(resetDay) {
            val targetIndex = RESET_DAYS.indexOf(resetDay)
            if (targetIndex != -1) {
                val visibleItems = listState.layoutInfo.visibleItemsInfo.size.coerceAtLeast(1)
                val centeredIndex = (targetIndex - visibleItems / 2).coerceIn(0, RESET_DAYS.lastIndex)
                listState.scrollToItem(centeredIndex)
            }
        }

        LazyRow(
            state = listState,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            items(RESET_DAYS) { day ->
                ResetDayChip(
                    day = day,
                    selected = day == resetDay,
                    onClick = { onResetDayChange(day) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.settings_sheet_explanation, resetDay),
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onSave
                )
                .background(Violet, RoundedCornerShape(16.dp))
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.settings_sheet_save_button),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun ResetDayChip(
    day: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Violet else CardBackground
    val textColor = if (selected) TextPrimary else TextSecondary

    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun ResetDayChipPreview() {
    BudgeeTheme {
        Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
            ResetDayChip(day = 20, selected = false, onClick = {})
            ResetDayChip(day = 21, selected = true, onClick = {})
            ResetDayChip(day = 22, selected = false, onClick = {})
        }
    }
}