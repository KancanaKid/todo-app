package com.kancanakid.todo.app.features.domain.util

/**
 * Filter states
 */
sealed class TaskOrder {
    object All : TaskOrder()
    object Completed : TaskOrder()
    object Active : TaskOrder()
}
