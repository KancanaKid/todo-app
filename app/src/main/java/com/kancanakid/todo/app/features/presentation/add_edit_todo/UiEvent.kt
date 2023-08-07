package com.kancanakid.todo.app.features.presentation.add_edit_todo

sealed class UiEvent {
    data class ShowSnackBar(val message:String):UiEvent()
    object SaveTask : UiEvent()
    object DeleteTask : UiEvent()
}
