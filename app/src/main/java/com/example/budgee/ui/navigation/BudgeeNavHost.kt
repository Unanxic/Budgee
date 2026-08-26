package com.example.budgee.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.budgee.ui.components.BudgeeBottomNav
import com.example.budgee.ui.components.BudgeeTab
import com.example.budgee.ui.screens.HistoryScreen
import com.example.budgee.ui.screens.HomeScreen
import com.example.budgee.ui.theme.AppBackground
import kotlinx.coroutines.launch

private val TABS = listOf(BudgeeTab.HOME, BudgeeTab.HISTORY)

@Composable
fun BudgeeNavHost() {
    val pagerState = rememberPagerState(pageCount = { TABS.size })
    val coroutineScope = rememberCoroutineScope()
    val selectedTab = TABS[pagerState.currentPage]

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppBackground,
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            BudgeeBottomNav(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    val targetPage = TABS.indexOf(tab)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(targetPage)
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (TABS[page]) {
                BudgeeTab.HOME -> HomeScreen()
                BudgeeTab.HISTORY -> HistoryScreen()
            }
        }
    }
}