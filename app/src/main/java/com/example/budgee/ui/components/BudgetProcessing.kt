package com.example.budgee.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.budgee.R
import com.example.budgee.ui.animations.animateProgressAsState
import com.example.budgee.ui.theme.Amber
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.Mint
import com.example.budgee.ui.theme.Rose
import com.example.budgee.ui.theme.TextPrimary
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.utils.toPercentString

/**
 * Circular progress ring showing budget usage percentage.
 * Color shifts: mint (low usage) -> amber (>60%) -> rose (critical, >90%).
 *
 * @param usedFraction usage ratio, 0f..1f (e.g. 0.25f = 25% used)
 */
@Composable
fun BudgetProgressRing(
    usedFraction: Float,
    modifier: Modifier = Modifier,
    size: Dp = 160.dp,
    strokeWidth: Dp = 10.dp
) {
    val clampedFraction = usedFraction.coerceIn(0f, 1f)
    val animatedFraction by animateProgressAsState(
        targetValue = clampedFraction,
        label = "budgetProgress"
    )

    val ringColor = when {
        clampedFraction >= 0.9f -> Rose
        clampedFraction >= 0.6f -> Amber
        else -> Mint
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            val diameter = size.toPx() - strokeWidth.toPx()
            val topLeft = Offset(strokeWidth.toPx() / 2f, strokeWidth.toPx() / 2f)
            val arcSize = Size(diameter, diameter)

            // Background track (faint)
            drawArc(
                color = Color.White.copy(alpha = 0.08f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
            // Progress arc
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedFraction,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = animatedFraction.toPercentString(),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
            Text(
                text = stringResource(R.string.budget_progress_usage_label),
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun BudgetProgressRingPreview() {
    BudgeeTheme {
        BudgetProgressRing(usedFraction = 0.25f)
    }
}