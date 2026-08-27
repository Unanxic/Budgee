package com.example.budgee.utils

private const val MAX_DECIMAL_DIGITS = 2

/**
 * Appends a digit to a raw amount-entry string (as typed on a numeric
 * keypad), respecting a max of [MAX_DECIMAL_DIGITS] digits after the
 * decimal separator, if one is present.
 */
fun String.appendDigit(digit: String): String {
    val decimalIndex = indexOf(',')
    if (decimalIndex != -1 && length - decimalIndex - 1 >= MAX_DECIMAL_DIGITS) {
        return this
    }
    if (this == "0") return digit
    return this + digit
}

/**
 * Appends a decimal separator, if one isn't already present.
 */
fun String.appendDecimal(): String {
    if (contains(',')) return this
    return if (isEmpty()) "0," else "$this,"
}

/**
 * Removes the last character, collapsing to an empty string rather than
 * a lone "0" when everything has been deleted.
 */
fun String.backspaceAmount(): String {
    if (isEmpty()) return this
    return dropLast(1)
}

/**
 * Parses a raw amount-entry string (Greek decimal comma) into a Double,
 * defaulting to 0.0 for blank/invalid input.
 */
fun String.toAmountDouble(): Double {
    return replace(',', '.').toDoubleOrNull() ?: 0.0
}