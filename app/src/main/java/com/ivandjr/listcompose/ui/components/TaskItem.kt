package com.ivandjr.listcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ivandjr.listcompose.R
import com.ivandjr.listcompose.model.Task
import com.ivandjr.listcompose.ui.theme.spacingTheme
import com.ivandjr.listcompose.util.toColor
import com.ivandjr.listcompose.util.toText

@Composable
fun TaskItem(task: Task, onClickRemove: (task: Task) -> Unit) {
    val title = task.title
    val description = task.description
    val priority = task.priority

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacingTheme.medium)
            .semantics(mergeDescendants = true) {},
    ) {
        val paddingCard = MaterialTheme.spacingTheme.small
        ConstraintLayout(
            modifier = Modifier
                .padding(start = paddingCard, top = paddingCard, bottom = paddingCard)
                .fillMaxWidth(),
        ) {
            val (txtTitle, txtDescription, txtPriority) = createRefs()
            val (cardPriority, btnDelete) = createRefs()

            Text(
                text = title ?: "--",
                modifier = Modifier.padding(bottom = paddingCard).constrainAs(txtTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            )

            Text(
                text = description ?: "",
                modifier = Modifier.padding(bottom = paddingCard).constrainAs(txtDescription) {
                    top.linkTo(txtTitle.bottom, margin = MaterialTheme.spacingTheme.small)
                    start.linkTo(parent.start)
                },
            )

            Text(
                text = "Nível de prioridade: ",
                modifier = Modifier.constrainAs(txtPriority) {
                    top.linkTo(cardPriority.top)
                    bottom.linkTo(cardPriority.bottom)
                },
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = priority.toColor()),
                modifier = Modifier
                    .size(24.dp)
                    .semantics {
                        contentDescription = priority.toText()
                    }
                    .constrainAs(cardPriority) {
                        top.linkTo(txtDescription.bottom, margin = 8.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(txtPriority.end, margin = 8.dp)
                    },
            ) {}

            IconButton(
                onClick = { onClickRemove.invoke(task) },
                modifier = Modifier
                    .semantics {
                        contentDescription = "Remoção da tarefa"
                    }
                    .constrainAs(btnDelete) {
                        top.linkTo(cardPriority.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(cardPriority.bottom)
                    },
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskItemPreview() {
    TaskItem(
        Task(
            id = "",
            title = "Teste de título",
            description = "descrição do conteúdo que pode ser muito grande",
        ),
        onClickRemove = {},
    )
}
