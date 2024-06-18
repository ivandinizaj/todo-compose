package com.ivandjr.listcompose.ui.screens.saveTask

import com.ivandjr.listcompose.ui.components.RadioGroupItem
import com.ivandjr.listcompose.model.Priority
import com.ivandjr.listcompose.ui.base.ViewEvent
import com.ivandjr.listcompose.ui.base.ViewSideEffect
import com.ivandjr.listcompose.ui.base.ViewState

internal class SaveTaskContract {

    sealed class Event : ViewEvent {
        data class TitleChanged(val title: String) : Event()
        data class DescriptionChanged(val description: String) : Event()
        data class PriorityChanged(val priority: Int?) : Event()
        data object Submit : Event()
    }

    data class State(
        var title: String,
        var titleError: String?,
        var description: String,
        var dataPriority: List<RadioGroupItem>,
        var descriptionError: String?,
        var priority: Priority?,
        var priorityId: Int?,
        var priorityError: String?,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object Idle : Effect()
        data object Success : Effect()
    }
}
