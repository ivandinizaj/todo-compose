package com.ivandjr.listcompose.ui.screens.listTask.viewmodel

import androidx.lifecycle.viewModelScope
import com.ivandjr.listcompose.domain.useCase.FetchTasksUseCase
import com.ivandjr.listcompose.domain.useCase.RemoveTaskUseCase
import com.ivandjr.listcompose.model.Task
import com.ivandjr.listcompose.ui.base.BaseViewModel
import com.ivandjr.listcompose.ui.screens.listTask.ListTaskContract.Effect
import com.ivandjr.listcompose.ui.screens.listTask.ListTaskContract.Event
import com.ivandjr.listcompose.ui.screens.listTask.ListTaskContract.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class ListTaskViewModel(
    private val fetchUseCase: FetchTasksUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<Event, State, Effect>() {

    init {
        fetchTasks()
    }

    override fun setInitialState() = State(
        listData = listOf(),
        isLoading = false,
        taskToRemove = null,
    )

    override fun onEvents(event: Event) {
        when (event) {
            is Event.RemoveTask -> {
                setState { copy(taskToRemove = event.task) }
                setEffect { Effect.RemoveTask }
            }
            is Event.RetryAgain -> fetchTasks()
            is Event.FinishRemoveDialog -> setEffect { Effect.FinishDialogRemove }
            is Event.ConfirmationRemoveDialog -> {
                removeTask(viewState.value.taskToRemove)
            }
        }
    }

    private fun fetchTasks() = viewModelScope.launch(dispatcher) {
        val scope = this
        scope.setState { copy(isLoading = true) }
        fetchUseCase.invoke().catch {
            setState { copy(isLoading = false) }
        }.onEach { list ->
            setState { copy(isLoading = false, listData = list) }
        }.collect()
    }

    private fun removeTask(task: Task?) {
        runCatching {
            removeTaskUseCase.removeTask(task?.id)
            setState { copy(taskToRemove = null) }
        }.onSuccess {
            setEffect { Effect.ConfirmationRemove }
        }.onFailure {
            setEffect { Effect.FinishDialogRemove }
        }
    }
}
