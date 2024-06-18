package com.ivandjr.listcompose.ui.screens.listTask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.ivandjr.listcompose.data.repository.TaskRepository
import com.ivandjr.listcompose.data.source.DataSource
import com.ivandjr.listcompose.domain.useCase.FetchTasksUseCase
import com.ivandjr.listcompose.domain.useCase.RemoveTaskUseCase

internal class ListTaskViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        try {
            if (modelClass.isAssignableFrom(ListTaskViewModel::class.java)) {
                val source = DataSource()
                val repository = TaskRepository(source)
                val fetchUseCase = FetchTasksUseCase(repository)
                val removeUseCase = RemoveTaskUseCase(repository)

                return ListTaskViewModel(fetchUseCase, removeUseCase) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        } catch (exception: Exception) {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
