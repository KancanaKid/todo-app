package com.kancanakid.todo.app.features.domain.usecase

import com.kancanakid.todo.app.features.domain.model.InvalidTaskException
import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.repositories.TaskRepository
import kotlin.jvm.Throws

class AddTaskUseCase(
    private val repository: TaskRepository
) {

    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task){
        if(task.title.isBlank()){
            throw InvalidTaskException("The title can't be empty")
        }
        if(task.dueDate <= 0){
            throw InvalidTaskException("The due date should be defined")
        }
        if(task.description.isBlank()){
            throw InvalidTaskException("The description can't be empty")
        }
        repository.insertTask(task)
    }
}