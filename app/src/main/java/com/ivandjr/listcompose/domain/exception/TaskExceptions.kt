package com.ivandjr.listcompose.domain.exception

internal sealed class TaskExceptions(override val message: String) : Exception(message) {
    data object TitleTaskEmptyException : TaskExceptions(message = "Não pode ficar limpo")
    data object DescriptionTaskEmptyException : TaskExceptions(message = "Não pode ficar limpo")
    data object PriorityEmptyException : TaskExceptions(message = "Não pode ficar limpo")
    data object IDEmptyException : TaskExceptions(message = "Não pode ficar limpo")
    data object IllegalUUIDException : TaskExceptions(message = "UUI Inválido")
}
