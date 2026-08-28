package com.example.budgee.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgee.ui.theme.BudgeeTheme

/**
 * Full-size centered loading indicator, shown while a StateFlow is
 * still in its Loading state (e.g. before Room/DataStore emit their
 * first value). Uses a looping Lottie animation instead of a plain
 * spinner.
 */
@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottiePlayer(
            assetFileName = "loader-three-dots.json",
            loop = true
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF100B1C)
@Composable
private fun LoadingViewPreview() {
    BudgeeTheme {
        LoadingView()
    }
}