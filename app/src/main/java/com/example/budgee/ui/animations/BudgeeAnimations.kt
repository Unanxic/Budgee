package com.example.budgee.ui.animations

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

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