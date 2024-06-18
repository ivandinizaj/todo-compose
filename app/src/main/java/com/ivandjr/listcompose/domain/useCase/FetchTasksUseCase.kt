package com.ivandjr.listcompose.domain.useCase

import com.ivandjr.listcompose.data.repository.TaskRepository
import com.ivandjr.listcompose.model.Task
import kotlinx.coroutines.flow.Flow

internal class FetchTasksUseCase(
    private val repository: TaskRepository,
) {
    fun invoke(): Flow<List<Task>> = repository.fetchTasks()
}
