package com.ivandjr.listcompose.ui.screens.listTask

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ivandjr.listcompose.R
import com.ivandjr.listcompose.model.Priority
import com.ivandjr.listcompose.model.Task
import com.ivandjr.listcompose.ui.components.AlertDialog
import com.ivandjr.listcompose.ui.components.TaskItem
import com.ivandjr.listcompose.ui.screens.listTask.ListTaskContract.Effect
import com.ivandjr.listcompose.ui.screens.listTask.ListTaskContract.Event
import com.ivandjr.listcompose.ui.screens.listTask.ListTaskContract.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListTask(
    state: State,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = { Text("Lista de tarefas", fontWeight = FontWeight.Bold) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "saveTask") },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                    contentDescription = "Ícone de adicionar tarefa",
                )
            }
        },
    ) { innerPadding ->

        val openAlertDialog = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            effectFlow?.onEach { effect ->
                when (effect) {
                    is Effect.RemoveTask -> openAlertDialog.value = true
                    is Effect.FinishDialogRemove, Effect.ConfirmationRemove ->
                        openAlertDialog.value = false
                }
            }?.collect()
        }

        if (openAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { onEventSent(Event.FinishRemoveDialog) },
                onConfirmation = {
                    onEventSent(Event.ConfirmationRemoveDialog)
                    println("Confirmation registered")
                },
                dialogTitle = "Tem certeza que deseja remover?",
                dialogText = "Confirmando a tarefa \"${state.taskToRemove?.title}\" será removida",
                icon = Icons.Default.Close,
            )
        }

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            itemsIndexed(state.listData) { _, task ->
                TaskItem(task, onClickRemove = { item -> onEventSent(Event.RemoveTask(item)) })
            }
        }
    }
}

@Preview
@Composable
private fun TaskItemPreview() {
    ListTask(
        state = State(
            listOf(
                Task(
                    UUID.randomUUID().toString(),
                    "Teste",
                    "teste",
                    Priority.HIGH,
                ),
                Task(
                    UUID.randomUUID().toString(),
                    "Título grande para testar como se comporta",
                    "Descrição também",
                    Priority.MEDIUM,
                ),
                Task(
                    UUID.randomUUID().toString(),
                    "Título grande para testar como se comporta",
                    "Descrição também",
                    Priority.LOW,
                ),
            ),
            false,
            null,
        ),
        effectFlow = flow {},
        onEventSent = {},
        navController = rememberNavController(),
    )
}

@Preview
@Composable
private fun TaskItemModalPreview() {
    ListTask(
        state = State(listOf(Task(UUID.randomUUID().toString(), "Teste", "teste")), false, null),
        effectFlow = flow { emit(Effect.RemoveTask) },
        onEventSent = {},
        navController = rememberNavController(),
    )
}
