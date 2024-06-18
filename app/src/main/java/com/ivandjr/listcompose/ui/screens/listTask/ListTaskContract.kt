package com.ivandjr.listcompose.ui.screens.listTask

import com.ivandjr.listcompose.model.Task
import com.ivandjr.listcompose.ui.base.ViewEvent
import com.ivandjr.listcompose.ui.base.ViewSideEffect
import com.ivandjr.listcompose.ui.base.ViewState

internal class ListTaskContract {

    sealed class Event : ViewEvent {
        data object RetryAgain : Event()
        data class RemoveTask(val task: Task) : Event()
        data object FinishRemoveDialog : Event()
        data object ConfirmationRemoveDialog : Event()
    }

    data class State(
        val listData: List<Task> = listOf(),
        val isLoading: Boolean,
        val taskToRemove: Task?,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object RemoveTask : Effect()
        data object FinishDialogRemove : Effect()
        data object ConfirmationRemove : Effect()
    }
}
