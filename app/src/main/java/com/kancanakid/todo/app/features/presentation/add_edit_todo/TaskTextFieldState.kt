package com.kancanakid.todo.app.features.presentation.add_edit_todo

data class TaskTextFieldState(
    val text:String = "",
    val hint:String = "",
    val isHintVisible:Boolean = true
)
