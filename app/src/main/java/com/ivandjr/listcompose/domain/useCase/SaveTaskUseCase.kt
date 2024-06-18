package com.ivandjr.listcompose.domain.useCase

import com.ivandjr.listcompose.data.repository.TaskRepository
import com.ivandjr.listcompose.domain.exception.TaskExceptions.DescriptionTaskEmptyException
import com.ivandjr.listcompose.domain.exception.TaskExceptions.PriorityEmptyException
import com.ivandjr.listcompose.domain.exception.TaskExceptions.TitleTaskEmptyException
import com.ivandjr.listcompose.model.Priority
import java.util.UUID

internal class SaveTaskUseCase(private val repository: TaskRepository) {
    fun saveTask(title: String?, description: String?, priority: Priority?) {
        when {
            title?.isNotEmpty() == true -> TitleTaskEmptyException
            description?.isNotEmpty() == true -> DescriptionTaskEmptyException
            priority == null -> PriorityEmptyException
        }
        repository.saveTask(
            id = UUID.randomUUID().toString(),
            title = title!!,
            description = description!!,
            priority = priority!!,
        )
    }
}
