package com.example.budgee.utils

import kotlin.math.roundToInt

/**
 * Formats a Float in the 0f..1f range as a whole-number percentage string,
 * e.g. 0.25f -> "25%", 0.9f -> "90%".
 */
fun Float.toPercentString(): String {
    return "${(this * 100).roundToInt()}%"
}