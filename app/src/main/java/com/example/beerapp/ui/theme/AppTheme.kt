package com.example.beerapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.example.beerapp.AppColorScheme
import com.example.beerapp.LocalAppColorScheme


private val darkColorScheme = AppColorScheme(
    primary = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    secondary = Color(0xFF1E1E1E),
    onSecondary = Color.White,
    background = Color(0xFF101010),
    onBackground = Color.White,
    warning = Color(0xFFC8553C)
)

@Composable
fun AppTheme(content: @Composable () -> Unit){
    val colorScheme = darkColorScheme
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current
}