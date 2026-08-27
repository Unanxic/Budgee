package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.CardBackground
import com.example.budgee.ui.theme.TextPrimary

private object NumericKeypadKey {
    const val DECIMAL = "\u0000decimal"
    const val BACKSPACE = "\u0000backspace"
}

private val KEYPAD_ROWS = listOf(
    listOf("1", "2", "3"),
    listOf("4", "5", "6"),
    listOf("7", "8", "9"),
    listOf(NumericKeypadKey.DECIMAL, "0", NumericKeypadKey.BACKSPACE)
)

/**
 * A 3x4 numeric keypad (1-9, decimal separator, 0, backspace) used for
 * entering monetary amounts.
 */
@Composable
fun NumericKeypad(
    onDigitPress: (String) -> Unit,
    onDecimalPress: () -> Unit,
    onBackspacePress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        KEYPAD_ROWS.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { key ->
                    when (key) {
                        NumericKeypadKey.DECIMAL -> KeypadKey(
                            modifier = Modifier.weight(1f),
                            onClick = onDecimalPress
                        ) {
                            Text(
                                text = stringResource(R.string.numpad_decimal_separator),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                        }

                        NumericKeypadKey.BACKSPACE -> KeypadKey(
                            modifier = Modifier.weight(1f),
                            onClick = onBackspacePress
                        ) {
                            Text(
                                text = "⌫",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextPrimary
                            )
                        }

                        else -> KeypadKey(
                            modifier = Modifier.weight(1f),
                            onClick = { onDigitPress(key) }
                        ) {
                            Text(
                                text = key,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun KeypadKey(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1.6f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .background(CardBackground, RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun NumericKeypadPreview() {
    BudgeeTheme {
        NumericKeypad(
            onDigitPress = {},
            onDecimalPress = {},
            onBackspacePress = {}
        )
    }
}