package com.ivandjr.listcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
//     Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
)

object AppCustomTypography {

    val h1: TextStyle
        @Composable
        get() = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
        )

    val h2: TextStyle
        @Composable
        get() = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
        )

    val body1: TextStyle
        @Composable
        get() = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.primary,
        )

    val body2: TextStyle
        @Composable
        get() = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
        )
}
