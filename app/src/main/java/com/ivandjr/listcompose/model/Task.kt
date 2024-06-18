package com.ivandjr.listcompose.model

data class Task(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val priority: Priority? = null,
)

enum class Priority {
    LOW,
    MEDIUM,
    HIGH,
}
