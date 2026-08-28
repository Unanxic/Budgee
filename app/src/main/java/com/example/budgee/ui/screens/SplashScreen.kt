package com.example.budgee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgee.ui.components.LottiePlayer
import com.example.budgee.ui.theme.AppBackground
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

/**
 * Full-screen splash shown right after the system splash, while the app
 * plays a short Lottie animation before navigating to the main tabs.
 *
 * @param onFinished called once the splash duration has elapsed, should
 *        trigger navigation to the main tabs.
 * @param durationMillis how long the splash stays visible before
 *        [onFinished] fires. Keep short (fintech apps: ~1.5-2.5s).
 */
@Composable
fun SplashScreen(
    onFinished: () -> Unit,
    durationMillis: Long = 2000L
) {
    LaunchedEffect(Unit) {
        delay(durationMillis.milliseconds)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentAlignment = Alignment.Center
    ) {
        LottiePlayer(
            assetFileName = "budgee_blob.json",
            size = 220.dp,
            loop = false
        )
    }
}