package com.nexus.studio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Monaco & Geist Light SaaS Color Tokens (Pure White Plate, Sharp Dark Accent)
val PureWhite = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFFAFAFA)
val BorderLight = Color(0xFFE4E4E7)
val TextPrimary = Color(0xFF09090B)
val TextSecondary = Color(0xFF52525B)
val TextMuted = Color(0xFF71717A)
val TextDim = Color(0xFFA1A1AA)

val PrimaryBlue = Color(0xFF2563EB)
val PrimaryIndigo = Color(0xFF4F46E5)
val AccentPurple = Color(0xFF7C3AED)

private val LightColorScheme = lightColorScheme(
    primary = TextPrimary,
    onPrimary = PureWhite,
    primaryContainer = SurfaceLight,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = PureWhite,
    background = PureWhite,
    onBackground = TextPrimary,
    surface = PureWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = TextSecondary,
    outline = BorderLight,
    outlineVariant = BorderLight
)

@Composable
fun NexusStudioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
