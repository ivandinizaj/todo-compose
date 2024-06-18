package com.ivandjr.listcompose.domain.useCase.validate

import com.ivandjr.listcompose.domain.exception.TaskExceptions

internal class DescriptionValidateUseCase {
    fun execute(description: String?): ValidationResult {
        if (description.isNullOrBlank()) {
            return ValidationResult(TaskExceptions.DescriptionTaskEmptyException)
        }
        return ValidationResult()
    }
}
