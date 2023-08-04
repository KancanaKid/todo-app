package com.kancanakid.todo.app.features.presentation.todos

import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.util.TaskOrder

data class TasksState(
    val tasks:List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.All,
    val isOrderSectionVisible:Boolean = false
)
