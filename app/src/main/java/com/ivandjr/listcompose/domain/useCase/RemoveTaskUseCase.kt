package com.ivandjr.listcompose.domain.useCase

import com.ivandjr.listcompose.data.repository.TaskRepository
import com.ivandjr.listcompose.domain.exception.TaskExceptions
import com.ivandjr.listcompose.util.isValidUUID

internal class RemoveTaskUseCase(private val repository: TaskRepository) {
    fun removeTask(id: String?) {
        when {
            id.isNullOrBlank() -> TaskExceptions.IDEmptyException
            id.isValidUUID() -> TaskExceptions.IllegalUUIDException
        }
        repository.removeTask(id!!)
    }
}
