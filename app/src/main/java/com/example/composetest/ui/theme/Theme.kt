package com.example.composetest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = White,
    primaryVariant = Grey,
    secondary = DarkGrey
)

private val LightColorPalette = lightColors(
    primary = White,
    onPrimary = Grey,
    primaryVariant = Grey,
    secondary = LightWhite,
    onSecondary = DarkGrey,
    background = White,
    onBackground = Grey,
    surface = LightWhite,
    onSurface = DarkGrey
)

@Composable
fun BusinessWatcherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    /*
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

     */

    LightColorPalette

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}