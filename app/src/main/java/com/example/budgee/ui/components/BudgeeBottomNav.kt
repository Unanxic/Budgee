package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgee.R
import com.example.budgee.ui.animations.animateTabRippleAsState
import com.example.budgee.ui.animations.animateTabUnderlineAsState
import com.example.budgee.ui.theme.AppBackground
import com.example.budgee.ui.theme.BudgeeTheme
import com.example.budgee.ui.theme.TextSecondary
import com.example.budgee.ui.theme.Violet

enum class BudgeeTab {
    HOME,
    HISTORY
}

@Composable
fun BudgeeBottomNav(
    selectedTab: BudgeeTab,
    onTabSelected: (BudgeeTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppBackground)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(
            label = stringResource(R.string.bottom_nav_home),
            selected = selectedTab == BudgeeTab.HOME,
            onClick = { onTabSelected(BudgeeTab.HOME) }
        )
        BottomNavItem(
            label = stringResource(R.string.bottom_nav_history),
            selected = selectedTab == BudgeeTab.HISTORY,
            onClick = { onTabSelected(BudgeeTab.HISTORY) }
        )
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val revealProgress by animateTabRippleAsState(
        targetValue = if (selected) 1f else 0f,
        label = "tabRippleReveal"
    )
    val underlineWidth by animateTabUnderlineAsState(
        targetValue = if (selected) 28.dp else 0.dp,
        label = "tabUnderlineWidth"
    )

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = label,
                color = Violet,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.drawWithContent {
                    val maxRadius = size.maxDimension * 0.9f
                    val radius = maxRadius * revealProgress
                    if (radius > 0f) {
                        val path = Path().apply {
                            addOval(
                                androidx.compose.ui.geometry.Rect(
                                    center = Offset(size.width / 2f, size.height / 2f),
                                    radius = radius
                                )
                            )
                        }
                        clipPath(path) {
                            this@drawWithContent.drawContent()
                        }
                    }
                }
            )
        }
        Row(modifier = Modifier.padding(top = 6.dp)) {
            Box(
                modifier = Modifier
                    .width(underlineWidth)
                    .height(3.dp)
                    .background(Violet, RoundedCornerShape(2.dp))
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun BudgeeBottomNavPreview() {
    BudgeeTheme {
        BudgeeBottomNav(
            selectedTab = BudgeeTab.HOME,
            onTabSelected = {}
        )
    }
}