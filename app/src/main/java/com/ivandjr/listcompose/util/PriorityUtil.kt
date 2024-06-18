package com.ivandjr.listcompose.util

import androidx.compose.ui.graphics.Color
import com.ivandjr.listcompose.model.Priority

internal fun Priority?.toColor(): Color {
    return when (this) {
        Priority.LOW -> Color.Green
        Priority.MEDIUM -> Color.Yellow
        Priority.HIGH -> Color.Red
        else -> Color.White
    }
}

internal fun Priority?.toText(): String {
    return when (this) {
        Priority.LOW -> "Prioridade baixa"
        Priority.MEDIUM -> "Prioridade média"
        Priority.HIGH -> "Prioridade alta"
        else -> "Sem prioridade"
    }
}
