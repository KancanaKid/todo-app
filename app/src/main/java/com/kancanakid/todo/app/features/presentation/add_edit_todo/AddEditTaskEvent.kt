package com.kancanakid.todo.app.features.presentation.add_edit_todo

import androidx.compose.ui.focus.FocusState

/**
 * All actions mapped in add / edit screen.
 */

sealed class AddEditTaskEvent {
    data class EnteredTitle(val value:String) : AddEditTaskEvent()
    data class ChangeTitleFocus(val focusState: FocusState):AddEditTaskEvent()
    data class EnteredDescription(val value:String):AddEditTaskEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState):AddEditTaskEvent()
    data class EnteredDueDate(val value:String) : AddEditTaskEvent()
    data class CurrentSelectedDate(val value: Long):AddEditTaskEvent()
    object DeleteTask:AddEditTaskEvent()
    object SaveTask:AddEditTaskEvent()
}
