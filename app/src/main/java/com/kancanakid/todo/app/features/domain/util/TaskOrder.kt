package com.kancanakid.todo.app.features.domain.util

sealed class TaskOrder {
    object All : TaskOrder()
    object Completed : TaskOrder()
    object Active : TaskOrder()
}
