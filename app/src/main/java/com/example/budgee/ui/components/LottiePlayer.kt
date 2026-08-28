package com.example.budgee.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

/**
 * Plays a Lottie animation bundled in app/src/main/assets/.
 *
 * @param assetFileName e.g. "loading.json" — must live in assets/
 * @param loop whether the animation repeats indefinitely (true for
 *        loading spinners, false for one-shot animations like splash)
 */
@Composable
fun LottiePlayer(
    assetFileName: String,
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    loop: Boolean = true
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(assetFileName)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (loop) LottieConstants.IterateForever else 1
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(size)
    )
}