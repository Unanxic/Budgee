package com.example.budgee.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgee.R
import com.example.budgee.ui.theme.BorderSubtle
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.FillFaint
import com.example.budgee.ui.theme.FillFaintHover
import com.example.budgee.ui.theme.TextSubtle

/**
 * Whether this PillButton shows its default leading icon, a custom one,
 * or none at all. Defaults to the standard chevron-left ("back") icon,
 * since that's the most common use case (e.g. the "← Πίσω" button on
 * detail screens). Pass [Custom] or [None] to override per call site.
 */
sealed interface PillButtonIcon {
    data object Default : PillButtonIcon
    data class Custom(val painter: Painter) : PillButtonIcon
    data object None : PillButtonIcon
}

@Composable
fun PillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: PillButtonIcon = PillButtonIcon.Default,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "pillScale"
    )
    val shape = RoundedCornerShape(99.dp)
    val alpha = if (enabled) 1f else 0.4f

    Row(
        modifier = modifier
            .scale(scale)
            .clip(shape)
            .background(if (pressed) FillFaintHover else FillFaint)
            .border(BorderStroke(1.dp, BorderSubtle), shape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 13.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconPainter = when (icon) {
            is PillButtonIcon.Default -> painterResource(R.drawable.ic_chevron_left)
            is PillButtonIcon.Custom -> icon.painter
            is PillButtonIcon.None -> null
        }
        if (iconPainter != null) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                tint = TextSubtle.copy(alpha = TextSubtle.alpha * alpha),
                modifier = Modifier.size(15.dp)
            )
        }
        Text(
            text = text,
            color = TextSubtle.copy(alpha = TextSubtle.alpha * alpha),
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(name = "PillButton", showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun PillButtonPreview() {
    BudgeeTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PillButton(text = "Πίσω", onClick = {})
            PillButton(text = "Ρυθμίσεις", onClick = {}, icon = PillButtonIcon.None)
            PillButton(text = "Φίλτρα", onClick = {}, enabled = false, icon = PillButtonIcon.None)
        }
    }
}