package ru.nifontbus.lc6_firebase.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = PrimaryVariant,
    secondary = Secondary,
    secondaryVariant = SecondaryVariant,
    background = Background,
    surface = Surface,
    onPrimary = OnPrimary,
    onSecondary = OnSecondary,
    onBackground = OnBackground,
    onSurface = OnSurface,
    onError = OnError
)

private val DarkColorPalette = darkColors(
    primary = PrimaryNight,
    primaryVariant = PrimaryVariantNight,
    secondary = SecondaryNight,
    secondaryVariant = SecondaryVariantNight,
    background = BackgroundNight,
    surface = SurfaceNight,
    onPrimary = OnPrimaryNight,
    onSecondary = OnSecondaryNight,
    onBackground = OnBackgroundNight,
    onSurface = OnSurfaceNight,
    onError = OnErrorNight
)

@Composable
fun LC6_FirebaseTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}