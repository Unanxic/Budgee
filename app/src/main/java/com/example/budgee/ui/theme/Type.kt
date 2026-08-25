package com.example.budgee.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.budgee.R

// Fira Sans font family, loaded from res/font/. Only the weights actually
// used by the typography below are bundled, to keep APK size down.
val FiraSans = FontFamily(
    Font(R.font.fira_sans_regular, FontWeight.Normal),
    Font(R.font.fira_sans_medium, FontWeight.Medium),
    Font(R.font.fira_sans_semibold, FontWeight.SemiBold),
    Font(R.font.fira_sans_bold, FontWeight.Bold)
)

val Typography = Typography(
    // Large central amount (e.g. "€373,00")
    displayLarge = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Bold,
        fontSize = 44.sp,
        lineHeight = 48.sp,
        letterSpacing = (-0.5).sp
    ),
    // Screen / month titles (e.g. "Αύγουστος 2026", "Ιστορικό")
    headlineLarge = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    // Transaction row title (e.g. "Κινηματογράφος")
    titleMedium = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    // Regular body text
    bodyLarge = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    // Uppercase captions (e.g. "ΥΠΟΛΟΙΠΟ ΜΗΝΑ", "ΟΡΙΣΤΗΚΕ")
    labelMedium = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.2.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)