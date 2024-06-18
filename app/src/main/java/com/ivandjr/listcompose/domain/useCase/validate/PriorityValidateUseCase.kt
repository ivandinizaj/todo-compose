package com.ivandjr.listcompose.domain.useCase.validate

import com.ivandjr.listcompose.domain.exception.TaskExceptions
import com.ivandjr.listcompose.model.Priority

internal class PriorityValidateUseCase {
    fun execute(priority: Priority?): ValidationResult {
        if (priority == null) {
            return ValidationResult(TaskExceptions.PriorityEmptyException)
        }
        return ValidationResult()
    }
}
