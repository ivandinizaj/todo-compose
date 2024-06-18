package com.ivandjr.listcompose.ui.screens.saveTask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.ivandjr.listcompose.data.repository.TaskRepository
import com.ivandjr.listcompose.data.source.DataSource
import com.ivandjr.listcompose.domain.useCase.SaveTaskUseCase
import com.ivandjr.listcompose.domain.useCase.validate.DescriptionValidateUseCase
import com.ivandjr.listcompose.domain.useCase.validate.PriorityValidateUseCase
import com.ivandjr.listcompose.domain.useCase.validate.TitleValidateUseCase

internal class SaveTaskViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        try {
            if (modelClass.isAssignableFrom(SaveTaskViewModel::class.java)) {
                val source = DataSource()
                val repository = TaskRepository(source)
                val useCase = SaveTaskUseCase(repository)
                val titleValidateUseCase = TitleValidateUseCase()
                val descriptionValidateUseCase = DescriptionValidateUseCase()
                val priorityValidateUseCase = PriorityValidateUseCase()
                return SaveTaskViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    useCase = useCase,
                    titleValidateUseCase = titleValidateUseCase,
                    descriptionValidateUseCase = descriptionValidateUseCase,
                    priorityValidateUseCase = priorityValidateUseCase,
                ) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        } catch (exception: Exception) {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
