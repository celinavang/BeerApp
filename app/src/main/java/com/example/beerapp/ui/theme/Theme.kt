package com.example.beerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color(0xff191923),
    secondary = Color.Blue,
    tertiary = Pink40,
    background = Color(0xFF101010),
    onBackground = Color.White,
    primaryContainer = Color(0xFF101010)
)

@Composable
fun BeerAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = ColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}