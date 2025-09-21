package com.example.grabit.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

private val primaryLightColor = Color(0xFFF9A825) // Amber 800 (warm yellow, Light mode)
private val primaryDarkColor = Color(0xFFFFC107)  // Amber 500 (bright amber, Dark mode)

private val secondaryLightColor = Color(0xFF424242)        // Dark Gray (Light mode)
private val secondaryVariantLightColor = Color(0x80424242) // 50% opacity

private val secondaryDarkColor = Color(0xFFB0BEC5)         // Cool Gray (Dark mode)
private val secondaryVariantDarkColor = Color(0x66B0BEC5)  // 40% opacity

private val tertiaryLightColor = Color(0xFF26A69A)  // Teal 400
private val tertiaryDarkColor = Color(0xFF80CBC4)   // Teal 200

private val containerHighLightColor = Color(0x1FF9A825)
private val containerNormalLightColor = Color(0x0FF9A825)
private val containerLowLightColor = Color(0x0AF9A825)
private val containerB40LightColor = Color(0x0AFFC107)

private val containerHighDarkColor = Color(0x3DFFC107)
private val containerNormalDarkColor = Color(0x1FFFC107)
private val containerLowDarkColor = Color(0x14FFC107)
private val containerB40DarkColor = Color(0x06FFC107)

private val textPrimaryLightColor = Color(0xDE000000)
private val textSecondaryLightColor = Color(0x99000000)
private val textDisabledLightColor = Color(0x66000000)

private val textPrimaryDarkColor = Color(0xFFFFFFFF)
private val textSecondaryDarkColor = Color(0xDEFFFFFF)
private val textDisabledDarkColor = Color(0x99FFFFFF)

private val outlineLightColor = Color(0xFFEBEBEB)
private val outlineDarkColor = Color(0x14EBEBEB)

private val surfaceLightColor = Color(0xFFFFFFFF)
private val surfaceDarkColor = Color(0xFF121212) // slightly darker than your old #212121

private val successStatusColor = Color(0xFF34A853)      // Green
private val permissionWarningColor = Color(0xFFFFA000)  // Orange (stronger than FBBC05)
private val awarenessAlertColor = Color(0xFFD32F2F)     // Red

private val iconsBackgroundColor = Color(0xFFFFB300)   // Amber 600
private val locationMarkerColor = Color(0xFFFF5722)    // Deep Orange
private val markerInfoWindowColor = Color(0xE7E0E0E0)  // Light gray

@Composable
fun appGradient(): Brush {
    val colors = LocalAppColorScheme.current
    return Brush.linearGradient(
        listOf(
            colors.containerHigh.copy(0.15f),
            colors.containerHigh.copy(0.1f),
            colors.containerLow
        )
    )
}

internal val themeLightColorScheme = lightColorScheme().copy(
    primary = primaryLightColor,
    onPrimary = textPrimaryLightColor,
    background = surfaceLightColor,
    onBackground = textPrimaryLightColor,
    onSecondary = textPrimaryLightColor
)

internal val appLightColorScheme = AppColorScheme(
    primary = primaryLightColor,
    secondary = secondaryLightColor,
    secondaryVariant = secondaryVariantLightColor,
    tertiary = tertiaryDarkColor,
    tertiaryVariant = tertiaryLightColor,
    outline = outlineLightColor,
    surface = surfaceLightColor,
    textPrimary = textPrimaryLightColor,
    textSecondary = textSecondaryLightColor,
    textDisabled = textDisabledLightColor,
    outlineInverse = outlineDarkColor,
    textInversePrimary = textPrimaryDarkColor,
    textInverseDisabled = textDisabledDarkColor,
    textInverseSecondary = textSecondaryDarkColor,
    containerInverseHigh = containerHighDarkColor,
    containerNormalInverse = containerNormalDarkColor,
    secondaryInverseVariant = secondaryVariantDarkColor,
    containerHigh = containerHighLightColor,
    containerNormal = containerNormalLightColor,
    containerLow = containerLowLightColor,
    containerB40 = containerB40LightColor
)

internal val themeDarkColorScheme = darkColorScheme().copy(
    primary = primaryDarkColor,
    onPrimary = textPrimaryDarkColor,
    background = surfaceDarkColor,
    onBackground = textPrimaryDarkColor,
    onSecondary = textPrimaryDarkColor
)

internal val appDarkColorScheme = AppColorScheme(
    primary = primaryDarkColor,
    secondary = secondaryDarkColor,
    secondaryVariant = secondaryVariantDarkColor,
    tertiary = tertiaryLightColor,
    tertiaryVariant = tertiaryDarkColor,
    outline = outlineDarkColor,
    surface = surfaceDarkColor,
    textPrimary = textPrimaryDarkColor,
    textSecondary = textSecondaryDarkColor,
    textDisabled = textDisabledDarkColor,
    outlineInverse = outlineLightColor,
    textInversePrimary = textPrimaryLightColor,
    textInverseDisabled = textDisabledLightColor,
    textInverseSecondary = textSecondaryLightColor,
    containerInverseHigh = containerHighLightColor,
    containerNormalInverse = containerNormalLightColor,
    secondaryInverseVariant = secondaryVariantLightColor,
    containerHigh = containerHighDarkColor,
    containerNormal = containerNormalDarkColor,
    containerLow = containerLowDarkColor,
    containerB40 = containerB40DarkColor
)

val LocalAppColorScheme = staticCompositionLocalOf {
    appLightColorScheme
}

data class AppColorScheme(
    val primary: Color,
    val tertiary: Color,
    val tertiaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val surface: Color,
    val outline: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val outlineInverse: Color,
    val textInversePrimary: Color,
    val textInverseSecondary: Color,
    val textInverseDisabled: Color,
    val containerInverseHigh: Color,
    val containerNormalInverse: Color,
    val secondaryInverseVariant: Color,
    val containerHigh: Color,
    val containerNormal: Color,
    val containerLow: Color,
    val containerB40: Color,
    val onPrimary: Color = textPrimaryDarkColor,
    val onPrimaryVariant: Color = textPrimaryLightColor,
    val onSecondary: Color = textSecondaryDarkColor,
    val onDisabled: Color = textDisabledLightColor,
    val permissionWarning: Color = permissionWarningColor,
    val alertColor: Color = awarenessAlertColor,
    val iconsBackground: Color = iconsBackgroundColor,
    val locationMarker: Color = locationMarkerColor,
    val markerInfoWindow: Color = markerInfoWindowColor,
    val successColor: Color = successStatusColor,
) {
    val containerNormalOnSurface: Color
        get() {
            return containerNormal.compositeOver(surface)
        }
}
