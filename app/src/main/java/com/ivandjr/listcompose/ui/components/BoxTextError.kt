package com.ivandjr.listcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BoxTextError(text: String, modifier: Modifier? = Modifier) {
    Text(
        modifier = modifier ?: Modifier.fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.error,
    )
}

@Preview
@Composable
fun BoxTextErrorPreview() {
    BoxTextError(text = "Erro de mensagem")
}
