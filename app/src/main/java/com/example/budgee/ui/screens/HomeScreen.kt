package com.example.budgee.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgee.ui.components.BudgeeBottomNav
import com.example.budgee.ui.components.BudgeeTab
import com.example.budgee.ui.components.HomeTopBar
import com.example.budgee.ui.theme.AppBackground
import com.example.budgee.ui.theme.BudgeeTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(BudgeeTab.HOME) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppBackground,
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            BudgeeBottomNav(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeTopBar(
                periodLabel = "Αύγουστος 2026",
                periodRange = "21 Αυγ – 20 Σεπ",
                onSettingsClick = {}
            )
            Box(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenPreview() {
    BudgeeTheme {
        HomeScreen()
    }
}