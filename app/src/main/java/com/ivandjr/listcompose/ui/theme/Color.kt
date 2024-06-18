package com.ivandjr.listcompose.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.ivandjr.listcompose.R

val LightThemeColors: ColorScheme
    @Composable get() = lightColorScheme()

val DarkThemeColors: ColorScheme
    @Composable get() = darkColorScheme()

@Immutable
data class ExtendedColors(
    val dialogBackground: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        dialogBackground = Color.Unspecified,
    )
}

val lightExtendedColors: ExtendedColors
    @Composable
    get() = ExtendedColors(
        dialogBackground = colorResource(id = R.color.dialog_background),
    )

val darkExtendedColors: ExtendedColors
    @Composable
    get() = ExtendedColors(
        dialogBackground = colorResource(id = R.color.dark_dialog_background),
    )
