package com.example.budgee.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Budgee supports ONLY dark theme (purply), matching the design.
private val BudgeeColorScheme = darkColorScheme(
    primary = Violet,
    onPrimary = TextPrimary,
    secondary = Mint,
    onSecondary = Canvas,
    tertiary = Amber,
    onTertiary = Canvas,
    error = Rose,
    onError = TextPrimary,
    background = AppBackground,
    onBackground = TextPrimary,
    surface = CardBackground,
    onSurface = TextPrimary,
    surfaceVariant = ListRowBackground,
    onSurfaceVariant = TextSecondary,
    outline = BorderColor,
)

@Composable
fun BudgeeTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            // Status bar icons should be light, since our background is dark.
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = BudgeeColorScheme,
        typography = Typography,
        content = content
    )
}