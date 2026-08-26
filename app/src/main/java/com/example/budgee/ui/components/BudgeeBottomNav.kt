package com.example.budgee.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgee.R
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
            .navigationBarsPadding()
            .padding(vertical = 14.dp),
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
    val tint = if (selected) Violet else TextSecondary

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
        Text(
            text = label,
            color = tint,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
        if (selected) {
            Row(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height(3.dp)
                        .background(Violet, RoundedCornerShape(2.dp))
                )
            }
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