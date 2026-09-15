package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Navy950,
    primaryContainer = CyanBadgeBg,
    onPrimaryContainer = NeonCyan,
    secondary = NeonEmerald,
    onSecondary = Navy950,
    secondaryContainer = EmeraldBadgeBg,
    onSecondaryContainer = NeonEmerald,
    tertiary = AccentAmber,
    onTertiary = Navy950,
    background = Navy950,
    onBackground = TextWhite,
    surface = Navy900,
    onSurface = TextWhite,
    surfaceVariant = Navy850,
    onSurfaceVariant = TextSlate400,
    outline = BorderNavy,
    outlineVariant = Navy700,
    error = AccentRose,
    onError = TextWhite
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
