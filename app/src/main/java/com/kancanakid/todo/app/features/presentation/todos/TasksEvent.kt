package com.kancanakid.todo.app.features.presentation.todos

import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.util.TaskOrder

sealed class TasksEvent{
    data class Order(val taskOrder: TaskOrder):TasksEvent()
    data class DeleteTask(val task:Task):TasksEvent()
    object RestoreTask:TasksEvent()
    object ToggleOrderSection:TasksEvent()
}
