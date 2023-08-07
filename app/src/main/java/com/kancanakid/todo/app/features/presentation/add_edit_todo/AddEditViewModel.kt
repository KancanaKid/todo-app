package com.kancanakid.todo.app.features.presentation.add_edit_todo

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kancanakid.todo.app.features.domain.model.InvalidTaskException
import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.usecase.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskTitle = mutableStateOf(
        TaskTextFieldState(
            hint = "Enter Title"
        )
    )
    val taskTitle: State<TaskTextFieldState> = _taskTitle

    private val _taskDueDate = mutableStateOf(
        TaskTextFieldState(
            hint = "Select Due Date"
        )
    )
    val taskDueDate: State<TaskTextFieldState> = _taskDueDate

    private val _taskDescription = mutableStateOf(
        TaskTextFieldState(
            hint = "Enter Description"
        )
    )
    val taskDescription: State<TaskTextFieldState> = _taskDescription

    private val _currentSelectedDate = mutableStateOf(TaskTextFieldState())
    private val currentSelectedDate: State<TaskTextFieldState> = _currentSelectedDate

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    val format = SimpleDateFormat("yyyy-MM-dd")

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if (taskId != -1) {
                viewModelScope.launch {
                    taskUseCases.getTask(taskId)?.also { task ->
                        currentTaskId = task.id
                        _currentSelectedDate.value = currentSelectedDate.value.copy(
                            text = task.dueDate.toString()
                        )
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                            isHintVisible = false
                        )
                        _taskDescription.value = taskDescription.value.copy(
                            text = task.description,
                            isHintVisible = false
                        )
                        _taskDueDate.value = taskDueDate.value.copy(
                            text = format.format(task.dueDate),
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditTaskEvent.ChangeTitleFocus -> {
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && taskTitle.value.text.isBlank()
                )
            }

            is AddEditTaskEvent.EnteredDescription -> {
                _taskDescription.value = taskDescription.value.copy(
                    text = event.value
                )
            }

            is AddEditTaskEvent.ChangeDescriptionFocus -> {
                _taskDescription.value = taskDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused && taskTitle.value.text.isBlank()
                )
            }

            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.addTask(
                            Task(
                                title = taskTitle.value.text,
                                dueDate = currentSelectedDate.value.text.toLong(),
                                description = taskDescription.value.text,
                                id = currentTaskId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTask)
                    } catch (e: InvalidTaskException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = "Couldn't save task")
                        )
                    }
                }
            }

            is AddEditTaskEvent.EnteredDueDate -> {
                _taskDueDate.value = taskDueDate.value.copy(
                    text = event.value
                )
            }

            is AddEditTaskEvent.CurrentSelectedDate -> {
                _currentSelectedDate.value = currentSelectedDate.value.copy(
                    text = event.value.toString()
                )
            }

            is AddEditTaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.deleteTask(
                            Task(
                                id = currentTaskId,
                                title = taskTitle.value.text,
                                dueDate = currentSelectedDate.value.text.toLong(),
                                description = taskDescription.value.text
                            )
                        )
                        _eventFlow.emit(UiEvent.DeleteTask)
                    }catch (e:InvalidTaskException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(message = "Couldn't delete task")
                        )
                    }
                }
            }
        }
    }
}