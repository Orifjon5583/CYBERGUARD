package com.cyberguard.security.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonCyanVariant,
    background = CyberDarkBg,
    surface = CyberCardBg,
    onPrimary = CyberDarkBg,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = AlertRed
)

@Composable
fun CyberGuardTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = CyberDarkBg.toArgb()
            window.navigationBarColor = NavBg.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
