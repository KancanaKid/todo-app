package com.kancanakid.todo.app.features.presentation.util

sealed class Screen(val route:String) {
    object TodoScreen:Screen("todo_screen")
    object AddEditScreen:Screen("add_edit_screen")
}
