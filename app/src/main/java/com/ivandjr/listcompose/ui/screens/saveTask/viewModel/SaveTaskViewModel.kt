package com.ivandjr.listcompose.ui.screens.saveTask.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ivandjr.listcompose.domain.useCase.SaveTaskUseCase
import com.ivandjr.listcompose.domain.useCase.validate.DescriptionValidateUseCase
import com.ivandjr.listcompose.domain.useCase.validate.PriorityValidateUseCase
import com.ivandjr.listcompose.domain.useCase.validate.TitleValidateUseCase
import com.ivandjr.listcompose.model.Priority
import com.ivandjr.listcompose.ui.base.BaseViewModel
import com.ivandjr.listcompose.ui.components.RadioGroupItem
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTaskContract.Effect
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTaskContract.Event
import com.ivandjr.listcompose.ui.screens.saveTask.SaveTaskContract.State
import com.ivandjr.listcompose.util.toColor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SaveTaskViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: SaveTaskUseCase,
    private val titleValidateUseCase: TitleValidateUseCase,
    private val descriptionValidateUseCase: DescriptionValidateUseCase,
    private val priorityValidateUseCase: PriorityValidateUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State(
        title = "",
        titleError = null,
        description = "",
        descriptionError = null,
        priority = null,
        priorityId = null,
        priorityError = null,
        dataPriority = getDataPriority(),
    )

    private fun getDataPriority(): List<RadioGroupItem> =
        Priority.entries.toTypedArray().mapIndexed { index, priority ->
            RadioGroupItem(id = index, text = priority.name, color = priority.toColor())
        }

    override fun onEvents(event: Event) {
        when (event) {
            is Event.DescriptionChanged -> {
                setState { copy(description = event.description) }
            }

            is Event.PriorityChanged -> {
                val priority = radioItemToPriority(event.priority)
                setState { copy(priority = priority, priorityId = event.priority) }
            }

            is Event.Submit -> submitData()
            is Event.TitleChanged -> setState { copy(title = event.title) }
        }
    }

    private fun submitData() {
        val state = viewState.value
        val titleResult = titleValidateUseCase.execute(state.title)
        val descriptionResult = descriptionValidateUseCase.execute(state.description)
        val priorityResult = priorityValidateUseCase.execute(state.priority)

        val hasError = listOf(
            titleResult,
            descriptionResult,
            priorityResult,
        ).any { !it.isSuccess }

        if (hasError) {
            setState {
                copy(
                    titleError = titleResult.exception?.message,
                    descriptionError = descriptionResult.exception?.message,
                    priorityError = priorityResult.exception?.message,
                )
            }
            return
        }

        saveTask(state.title, state.description, state.priority)
    }

    fun saveTask(title: String?, description: String?, priority: Priority?) {
        viewModelScope.launch(dispatcher) {
            useCase.saveTask(title, description, priority).runCatching {
            }.onFailure {
            }.onSuccess {
                setEffect { Effect.Success }
            }
        }
    }

    private fun radioItemToPriority(id: Int?): Priority? {
        val data = viewState.value.dataPriority.find { it.id == id }
        return data?.let { Priority.valueOf(it.text) }
    }
}
