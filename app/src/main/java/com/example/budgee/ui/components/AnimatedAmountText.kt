package com.example.budgee.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgee.ui.animations.animateAmountAsState
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.utils.toEuroString

/**
 * Large central amount (e.g. "€373,00") with a smooth count animation
 * when the value changes, instead of snapping abruptly. Used at the top
 * of the Home screen.
 */
@Composable
fun AnimatedAmountText(
    amount: Double,
    modifier: Modifier = Modifier
) {
    val animatedAmount by animateAmountAsState(
        targetValue = amount.toFloat(),
        label = "amountCountAnimation"
    )

    Text(
        text = animatedAmount.toDouble().toEuroString(),
        style = MaterialTheme.typography.displayLarge,
        color = TextPrimary,
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun AnimatedAmountTextPreview() {
    BudgeeTheme {
        AnimatedAmountText(amount = 373.00)
    }
}