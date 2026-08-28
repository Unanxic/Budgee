package com.example.budgee.ui.navigation

import androidx.compose.foundation.background
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.budgee.ui.components.BudgeeBottomNav
import com.example.budgee.ui.components.BudgeeTab
import com.example.budgee.ui.screens.HistoryScreen
import com.example.budgee.ui.screens.HomeScreen
import com.example.budgee.ui.screens.MonthDetailScreen
import com.example.budgee.ui.screens.SplashScreen
import com.example.budgee.ui.theme.AppBackground
import kotlinx.coroutines.launch

private object BudgeeDestinations {
    const val SPLASH = "splash"
    const val TABS = "tabs"
    const val MONTH_DETAIL = "month_detail/{monthId}"
    fun monthDetailRoute(monthId: Long) = "month_detail/$monthId"
    const val MONTH_ID_ARG = "monthId"
}

private val TABS = listOf(BudgeeTab.HOME, BudgeeTab.HISTORY)

@Composable
fun BudgeeNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BudgeeDestinations.SPLASH,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        enterTransition = slideInHorizontallyTransition,
        exitTransition = slideOutHorizontallyTransition,
        popEnterTransition = slideInOnReEnterHorizontallyTransition,
        popExitTransition = slideOutOnReEnterHorizontallyTransition
    ) {
        composable(BudgeeDestinations.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(BudgeeDestinations.TABS) {
                        popUpTo(BudgeeDestinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(BudgeeDestinations.TABS) {
            TabsHost(
                onMonthClick = { monthId ->
                    navController.navigate(BudgeeDestinations.monthDetailRoute(monthId))
                }
            )
        }
        composable(
            route = BudgeeDestinations.MONTH_DETAIL,
            arguments = listOf(navArgument(BudgeeDestinations.MONTH_ID_ARG) {
                type = NavType.LongType
            })
        ) {
            MonthDetailScreen(
                onBackClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun TabsHost(
    onMonthClick: (Long) -> Unit
) {
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
                BudgeeTab.HISTORY -> HistoryScreen(
                    onMonthClick = { month -> onMonthClick(month.id) }
                )
            }
        }
    }
}