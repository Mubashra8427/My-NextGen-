package com.nextgenacademy.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val NavyBlue = Color(0xFF0A2540)
val LightBlue = Color(0xFF38BDF8)
val AccentYellow = Color(0xFFFACC15)
val BackgroundWhite = Color(0xFFF8FAFC)
val SurfaceWhite = Color(0xFFFFFFFF)

private val LightColorScheme = lightColorScheme(
    primary = NavyBlue,
    secondary = LightBlue,
    tertiary = AccentYellow,
    background = BackgroundWhite,
    surface = SurfaceWhite,
)

@Composable
fun NextGenAcademyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
