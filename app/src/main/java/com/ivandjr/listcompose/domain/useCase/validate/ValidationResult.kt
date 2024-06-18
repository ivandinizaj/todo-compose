package com.ivandjr.listcompose.domain.useCase.validate

import com.ivandjr.listcompose.domain.exception.TaskExceptions

internal data class ValidationResult(val exception: TaskExceptions? = null) {
    val isSuccess = exception == null
}
