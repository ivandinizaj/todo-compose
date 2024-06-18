package com.ivandjr.listcompose.data.repository

import com.ivandjr.listcompose.data.source.DataSource
import com.ivandjr.listcompose.model.Priority
import com.ivandjr.listcompose.model.Task
import kotlinx.coroutines.flow.Flow

internal class TaskRepository(private val dataSource: DataSource) {
    fun saveTask(id: String, title: String, description: String, priority: Priority) {
        dataSource.saveTask(id, title, description, priority)
    }

    fun fetchTasks(): Flow<List<Task>> = dataSource.fetchTasks()

    fun removeTask(id: String) = dataSource.removeTask(id)
}
