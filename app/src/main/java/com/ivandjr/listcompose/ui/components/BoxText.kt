package com.ivandjr.listcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BoxText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    messageError: String? = "",

) {
    val isError = messageError?.isNotBlank() == true
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier,
        label = {
            Text(label)
        },
        isError = isError,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = messageError ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        trailingIcon = {
            if (isError) {
                Icon(Icons.Filled.Info, "error", tint = MaterialTheme.colorScheme.error)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
        ),
    )
}

@Composable
@Preview
private fun BoxTextPreview() {
    BoxText(
        value = "",
        label = "Digite o valor",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
    )
}

@Composable
@Preview
private fun BoxTextPreviewError() {
    BoxText(
        value = "",
        label = "Digite o valor",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { },
        messageError = "Número inválido",
    )
}
