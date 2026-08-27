package com.example.budgee.ui.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Central place for Budgee's shared animation specs and helpers, so that
 * timing/easing stays consistent across components instead of being
 * hardcoded inline in each composable.
 */
object BudgeeAnimations {

    /** Used for the balance counting up/down and similar numeric changes. */
    val amountChangeSpec: AnimationSpec<Float> = tween(durationMillis = 500)

    /** Used for the budget progress ring sweep. */
    val progressSweepSpec: AnimationSpec<Float> = tween(durationMillis = 600)

    /** Used for the bottom nav tab selection color-reveal ripple. */
    val tabRippleSpec: AnimationSpec<Float> = tween(durationMillis = 380)

    /** Used for the bottom nav selected-tab underline width. */
    val tabUnderlineSpec: AnimationSpec<Dp> = spring(dampingRatio = Spring.DampingRatioMediumBouncy)

    /** Used for the press-down scale shrink/grow on buttons. */
    val pressScaleSpec: AnimationSpec<Float> = tween(durationMillis = 120)

    /** Used for smooth color transitions, e.g. toggle selection state. */
    val colorChangeSpec: AnimationSpec<Color> = tween(durationMillis = 250)
}

/**
 * Animates a Float value using Budgee's standard "amount change" timing.
 * Wraps [animateFloatAsState] so call sites don't repeat the animationSpec.
 */
@Composable
fun animateAmountAsState(targetValue: Float, label: String): State<Float> {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = BudgeeAnimations.amountChangeSpec,
        label = label
    )
}

/**
 * Animates a Float value using Budgee's standard "progress sweep" timing,
 * e.g. for the circular budget usage ring.
 */
@Composable
fun animateProgressAsState(targetValue: Float, label: String): State<Float> {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = BudgeeAnimations.progressSweepSpec,
        label = label
    )
}

/**
 * Animates a Float value using Budgee's standard "tab ripple reveal"
 * timing, used for the bottom nav's color-fill selection animation.
 */
@Composable
fun animateTabRippleAsState(targetValue: Float, label: String): State<Float> {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = BudgeeAnimations.tabRippleSpec,
        label = label
    )
}

/**
 * Animates a Dp value using Budgee's standard "tab underline" spring
 * timing, used for the bottom nav's selected-tab underline width.
 */
@Composable
fun animateTabUnderlineAsState(targetValue: Dp, label: String): State<Dp> {
    return animateDpAsState(
        targetValue = targetValue,
        animationSpec = BudgeeAnimations.tabUnderlineSpec,
        label = label
    )
}

/**
 * Animates a Float value using Budgee's standard "press scale" timing,
 * used to shrink/grow buttons slightly on press for tactile feedback.
 */
@Composable
fun animatePressScaleAsState(targetValue: Float, label: String): State<Float> {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = BudgeeAnimations.pressScaleSpec,
        label = label
    )
}

/**
 * Animates a Color value using Budgee's standard "color change" timing,
 * used e.g. for smooth transitions between selected/unselected toggle
 * states.
 */
@Composable
fun animateBudgeeColorAsState(targetValue: Color, label: String): State<Color> {
    return animateColorAsState(
        targetValue = targetValue,
        animationSpec = BudgeeAnimations.colorChangeSpec,
        label = label
    )
}