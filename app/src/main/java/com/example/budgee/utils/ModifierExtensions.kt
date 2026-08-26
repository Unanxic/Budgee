package com.example.budgee.utils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.example.budgee.ui.animations.animatePressScaleAsState

private enum class PulsateButtonState { Pressed, Idle }

fun Modifier.pulsateClick(onClick: () -> Unit): Modifier = composed {
    var buttonState by remember { mutableStateOf(PulsateButtonState.Idle) }
    val scale by animatePressScaleAsState(
        targetValue = if (buttonState == PulsateButtonState.Pressed) 0.95f else 1f,
        label = "pulsateClickScale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == PulsateButtonState.Pressed) {
                    waitForUpOrCancellation()
                    PulsateButtonState.Idle
                } else {
                    awaitFirstDown(requireUnconsumed = false)
                    PulsateButtonState.Pressed
                }
            }
        }
}