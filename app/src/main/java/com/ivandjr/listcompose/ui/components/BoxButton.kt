package com.ivandjr.listcompose.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BoxButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick, modifier) {
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
@Preview
private fun BoxButtonPreview() {
    BoxButton(text = "Salvar", onClick = { })
}
