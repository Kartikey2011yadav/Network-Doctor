package com.example.network_doctor.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DarkBackground = Color(0xFF101522)
val DarkSurface = Color(0xFF1E2433)
val CyanAccent = Color(0xFF00E5FF)
val PurpleAccent = Color(0xFF651FFF)

val GoldStart = Color(0xFFFFD700)
val GoldEnd = Color(0xFFFFA500)

val Green200 = Color(0xFFAEFF82)
val Green500 = Color(0xFF07A312)

val LightColor = Color(0xFF414D66)
val Pink = Color(0xFFE2437E)

val MainGradient = Brush.verticalGradient(
    colors = listOf(DarkSurface, DarkBackground)
)

val PremiumGradient = Brush.horizontalGradient(
    colors = listOf(GoldStart, GoldEnd)
)

val GaugeGradient = Brush.horizontalGradient(
    colors = listOf(CyanAccent, PurpleAccent)
)

// Legacy colors to match existing usage in SpeedTestScreen until refactor
val DarkColor = DarkBackground
val DarkGradient = MainGradient
val GreenGradient = Brush.horizontalGradient(
    colors = listOf(Green500, Green200)
)