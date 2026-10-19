package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PlayfinityDarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = ObsidianBlack,
    primaryContainer = NeonBlueSecondary,
    onPrimaryContainer = TextPrimaryWhite,
    secondary = CyberBlueAccent,
    onSecondary = ObsidianBlack,
    secondaryContainer = CardSurfaceElevated,
    onSecondaryContainer = TextPrimaryWhite,
    tertiary = ElectricPurple,
    onTertiary = TextPrimaryWhite,
    background = ObsidianBlack,
    onBackground = TextPrimaryWhite,
    surface = DeepSpaceDark,
    onSurface = TextPrimaryWhite,
    surfaceVariant = CardSurfaceDark,
    onSurfaceVariant = TextSecondaryMuted,
    outline = BorderNeonSubtle
)

@Composable
fun PlayfinityGamesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PlayfinityDarkColorScheme,
        typography = Typography,
        content = content
    )
}
