package com.kancanakid.todo.app.features.domain.usecase

import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.repositories.TaskRepository
import com.kancanakid.todo.app.features.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(
        taskOrder: TaskOrder
    ):Flow<List<Task>> {
        return repository.getTasks().map { task ->
            when(taskOrder){
                is TaskOrder.All -> task
                is TaskOrder.Active -> task.filter {
                    !it.isCompleted
                }
                is TaskOrder.Completed -> task.filter {
                    it.isCompleted
                }
            }
        }
    }
}