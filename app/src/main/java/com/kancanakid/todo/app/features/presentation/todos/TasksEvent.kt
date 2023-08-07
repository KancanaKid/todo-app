package com.kancanakid.todo.app.features.presentation.todos

import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.util.TaskOrder

/**
 * All actions mapped in main screen.
 */

sealed class TasksEvent{
    data class Order(val taskOrder: TaskOrder):TasksEvent()
    data class SetComplete(val task:Task):TasksEvent()
    data class SetIncomplete(val task: Task):TasksEvent()
    object ToggleOrderSection:TasksEvent()
    object Refresh:TasksEvent()
}
