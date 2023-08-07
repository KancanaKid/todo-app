package com.kancanakid.todo.app.features.domain.usecase

import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.repositories.TaskRepository

class GetTaskUseCase(private val repository: TaskRepository) {

    suspend operator  fun invoke(id:Int):Task? {
        return repository.getTaskById(id)
    }
}