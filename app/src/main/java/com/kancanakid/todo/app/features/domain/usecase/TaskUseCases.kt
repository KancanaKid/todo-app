package com.kancanakid.todo.app.features.domain.usecase

data class TaskUseCases(
    val getTasks : GetTasksUseCase,
    val deleteTask : DeleteTaskUseCase,
    val addTask: AddTaskUseCase
)
