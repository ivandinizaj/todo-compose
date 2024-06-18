package com.ivandjr.listcompose.domain.useCase.validate

import com.ivandjr.listcompose.domain.exception.TaskExceptions

internal class TitleValidateUseCase {
    fun execute(title: String?): ValidationResult {
        if (title.isNullOrBlank()) {
            return ValidationResult(TaskExceptions.TitleTaskEmptyException)
        }
        return ValidationResult()
    }
}
