package com.kancanakid.todo.app.features.domain.usecase

import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.repositories.TaskRepository

class SetCompleteTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task:Task){
        task.id?.let {taskId ->
            repository.updateCompleted(taskId, true)
        }
    }
}