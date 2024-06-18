package com.ivandjr.listcompose.ui.screens.saveTask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ivandjr.listcompose.ui.components.BoxButton
import com.ivandjr.listcompose.ui.components.BoxText
import com.ivandjr.listcompose.ui.components.RadioGroup
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTaskContract.Effect
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTaskContract.Event
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTaskContract.State
import com.ivandjr.listcompose.ui.theme.spacingTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SaveTask(
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
                title = {
                    Text("Salvar tarefa", fontWeight = FontWeight.Bold)
                },
            )
        },

    ) { innerPadding ->

        LaunchedEffect(Unit) {
            effectFlow?.onEach { effect ->
                when (effect) {
                    Effect.Idle -> Unit
                    Effect.Success -> navController.popBackStack()
                }
            }?.collect()
        }

        val spacing = MaterialTheme.spacingTheme.medium

        fun handlePriorityChanged(index: Int) {
            onEventSent(Event.PriorityChanged(index))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
        ) {
            BoxText(
                value = state.title,
                onValueChange = {
                    onEventSent(Event.TitleChanged(it))
                },
                messageError = state.titleError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing, spacing, spacing, 0.dp),
                label = "Título tarefa",
            )

            BoxText(
                value = state.description,
                onValueChange = {
                    onEventSent(Event.DescriptionChanged(it))
                },
                messageError = state.descriptionError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing, spacing, spacing, 0.dp)
                    .height(140.dp),
                label = "Descrição",
                maxLines = 5,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing, spacing, spacing, 0.dp),
            ) {
                Text(text = "Nível de prioridade: ")
                RadioGroup(
                    selected = state.priorityId,
                    setSelected = { id -> handlePriorityChanged(id) },
                    listItems = state.dataPriority,
                    messageError = state.priorityError,
                )
            }

            BoxButton(
                text = "Salvar",
                onClick = { onEventSent(Event.Submit) },
                modifier = Modifier
                    .padding(spacing)
                    .fillMaxWidth(),
            )
        }
    }
}
