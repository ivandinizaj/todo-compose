package com.ivandjr.listcompose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun RadioGroup(
    listItems: List<RadioGroupItem> = listOf(),
    selected: Int? = null,
    label: String = "",
    setSelected: (Int) -> Unit,
    messageError: String? = null,
) {
    val itemSelected = listItems.find { it.id == selected }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ConstraintLayout {
            val (txtLabel, listRadio, txtError) = createRefs()
            Text(
                modifier = Modifier.constrainAs(txtLabel) {
                    top.linkTo(listRadio.top)
                    end.linkTo(listRadio.start)
                    bottom.linkTo(listRadio.bottom)
                },
                text = label,
            )
            Row(
                modifier = Modifier.constrainAs(listRadio) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                listItems.forEach { item ->
                    RadioButton(
                        selected = itemSelected == item,
                        onClick = { setSelected(item.id) },
                        Modifier.semantics {
                            contentDescription = item.text
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = item.color,
                            unselectedColor = item.color,
                        ),
                    )
                }
            }
            if (messageError?.isNotBlank() == true) {
                BoxTextError(
                    modifier = Modifier.constrainAs(txtError) {
                        top.linkTo(listRadio.bottom)
                        start.linkTo(parent.start)
                    },
                    text = messageError,
                )
            }
        }
    }
}

data class RadioGroupItem(
    val id: Int,
    val text: String,
    val color: Color,
)

@Preview
@Composable
private fun RadioGroupPreview() {
    RadioGroup(
        listItems = listOf(
            RadioGroupItem(1, "Item 1", Color.Yellow),
            RadioGroupItem(2, "Item 2", Color.Blue),
        ),
        setSelected = {},
    )
}

@Preview
@Composable
private fun RadioGroupWithLabelPreview() {
    RadioGroup(
        listItems = listOf(
            RadioGroupItem(1, "Item 1", Color.Yellow),
            RadioGroupItem(2, "Item 2", Color.Blue),
        ),
        label = "Nível de prioridade",
        setSelected = {},
    )
}

@Preview
@Composable
private fun RadioGroupPreviewWithError() {
    RadioGroup(
        listItems = listOf(
            RadioGroupItem(1, "Item 1", Color.Yellow),
            RadioGroupItem(2, "Item 2", Color.Blue),
            RadioGroupItem(3, "Item 2", Color.Blue),
            RadioGroupItem(4, "Item 2", Color.Blue),
        ),
        setSelected = {},
        label = "Nível de prioridade",
        messageError = "Prioridade precisa ser selecionada",
    )
}
