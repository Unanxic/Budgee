package com.example.budgee.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formats a transaction timestamp as a short Greek date label,
 * e.g. 1735689600000L -> "26 Αυγ".
 */
fun Long.toShortDateLabel(): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.forLanguageTag("el"))
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}