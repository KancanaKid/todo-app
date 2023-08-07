package com.kancanakid.todo.app.features.domain.usecase

/**
 * Sets of use cases that user can do with app
 */
data class TaskUseCases(
    val getTasks : GetTasksUseCase,
    val deleteTask : DeleteTaskUseCase,
    val addTask: AddTaskUseCase,
    val setCompleteTask : SetCompleteTaskUseCase,
    val getTask : GetTaskUseCase,
    val setIncompleteTask: SetIncompleteTaskUseCase
)
