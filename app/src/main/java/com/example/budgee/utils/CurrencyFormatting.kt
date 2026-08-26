package com.example.budgee.utils

import java.util.Locale

/**
 * Formats a Double as a euro amount with Greek-style decimal comma,
 * e.g. 373.0 -> "€373,00", 14.5 -> "€14,50".
 *
 * We build this manually (rather than java.text.NumberFormat with
 * Locale("el", "GR")) so the exact "€" + comma-decimal format is
 * guaranteed regardless of the device's locale settings.
 */
fun Double.toEuroString(): String {
    val formatted = String.format(Locale.US, "%.2f", this).replace('.', ',')
    return "€$formatted"
}

/**
 * Same as [toEuroString] but prefixes a "+" or "−" sign based on [isIncome].
 * Used in transaction rows, e.g. "+€120,00" or "−€14,50".
 */
fun Double.toSignedEuroString(isIncome: Boolean): String {
    val sign = if (isIncome) "+" else "−"
    return "$sign${this.toEuroString()}"
}