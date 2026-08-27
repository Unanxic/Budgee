package com.example.budgee.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.animations.animateBudgeeColorAsState
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.Canvas
import com.example.budgee.ui.theme.CardBackground
import com.example.budgee.ui.theme.Mint
import com.example.budgee.ui.theme.Rose
import com.example.budgee.ui.theme.TextMuted
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.utils.toAmountDouble

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionSheet(
    isIncome: Boolean,
    onIsIncomeChange: (Boolean) -> Unit,
    amountText: String,
    onDigitPress: (String) -> Unit,
    onDecimalPress: () -> Unit,
    onBackspacePress: () -> Unit,
    reason: String,
    onReasonChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    val targetAccentColor = if (isIncome) Mint else Rose
    val accentColor by animateBudgeeColorAsState(
        targetValue = targetAccentColor,
        label = "addTransactionAccentColor"
    )
    val sign = if (isIncome) "+" else "−"
    val isConfirmEnabled = amountText.toAmountDouble() > 0.0 && reason.isNotBlank()

    BudgeeBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState
    ) {
        TransactionTypeToggle(
            isIncome = isIncome,
            onIsIncomeChange = onIsIncomeChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        val displayAmount = if (amountText.isBlank() || amountText == "0") "0" else amountText
        Text(
            text = "$sign€$displayAmount",
            style = MaterialTheme.typography.displayLarge,
            color = accentColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = reason,
            onValueChange = onReasonChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
            singleLine = true,
            cursorBrush = SolidColor(accentColor),
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackground, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            decorationBox = { innerTextField ->
                if (reason.isEmpty()) {
                    Text(
                        text = stringResource(R.string.add_transaction_reason_placeholder),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextMuted
                    )
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        NumericKeypad(
            onDigitPress = onDigitPress,
            onDecimalPress = onDecimalPress,
            onBackspacePress = onBackspacePress
        )

        Spacer(modifier = Modifier.height(20.dp))

        val confirmBackground = if (isConfirmEnabled) accentColor else accentColor.copy(alpha = 0.18f)
        val confirmTextColor = if (!isConfirmEnabled) {
            accentColor.copy(alpha = 0.5f)
        } else if (isIncome) {
            Canvas
        } else {
            TextPrimary
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    enabled = isConfirmEnabled,
                    onClick = onConfirm
                )
                .background(confirmBackground, RoundedCornerShape(16.dp))
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(
                    if (isIncome) R.string.add_transaction_confirm_income
                    else R.string.add_transaction_confirm_expense
                ),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = confirmTextColor
            )
        }
    }
}

/**
 * Income/expense toggle with a sliding filled "pill" indicator that
 * animates between the two options (instead of two independently
 * color-fading backgrounds), matching a native switch-style interaction.
 */
@Composable
private fun TransactionTypeToggle(
    isIncome: Boolean,
    onIsIncomeChange: (Boolean) -> Unit
) {
    var containerWidthPx by remember { mutableFloatStateOf(0f) }
    val indicatorOffsetPx by animateFloatAsState(
        targetValue = if (isIncome) 0f else containerWidthPx / 2f,
        label = "toggleIndicatorOffset"
    )
    val indicatorColor by animateBudgeeColorAsState(
        targetValue = (if (isIncome) Mint else Rose).copy(alpha = 0.18f),
        label = "toggleIndicatorColor"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onIsIncomeChange(!isIncome) }
            )
            .onGloballyPositioned { containerWidthPx = it.size.width.toFloat() }
            .background(CardBackground, RoundedCornerShape(16.dp))
            .drawBehind {
                if (containerWidthPx > 0f) {
                    drawRoundRect(
                        color = indicatorColor,
                        topLeft = Offset(indicatorOffsetPx, 0f),
                        size = Size(containerWidthPx / 2f, size.height),
                        cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                    )
                }
            }
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ToggleLabel(
            label = stringResource(R.string.add_transaction_income_tab),
            selected = isIncome,
            accentColor = Mint,
            modifier = Modifier.weight(1f)
        )
        ToggleLabel(
            label = stringResource(R.string.add_transaction_expense_tab),
            selected = !isIncome,
            accentColor = Rose,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ToggleLabel(
    label: String,
    selected: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val textColor by animateBudgeeColorAsState(
        targetValue = if (selected) accentColor else TextMuted,
        label = "toggleLabelColor"
    )

    Box(
        modifier = modifier.padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            ),
            color = textColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun TransactionTypeTogglePreview() {
    BudgeeTheme {
        TransactionTypeToggle(isIncome = true, onIsIncomeChange = {})
    }
}