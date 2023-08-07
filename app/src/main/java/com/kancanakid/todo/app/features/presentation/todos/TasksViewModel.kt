package com.kancanakid.todo.app.features.presentation.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kancanakid.todo.app.features.domain.usecase.TaskUseCases
import com.kancanakid.todo.app.features.domain.util.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
):ViewModel(){

    private val _uiState = mutableStateOf(TasksState())
    val uiState: State<TasksState> = _uiState

    private var getTaskJob:Job? = null

    init {
        getTasks(TaskOrder.All)
    }

    fun onEvent(event: TasksEvent){
        when(event){
            is TasksEvent.Order -> {
                if(uiState.value.taskOrder::class == event.taskOrder::class){
                    return
                }
                getTasks(event.taskOrder)
            }
            is TasksEvent.ToggleOrderSection -> {
                _uiState.value = uiState.value.copy(
                    isOrderSectionVisible = !uiState.value.isOrderSectionVisible
                )

            }
            is TasksEvent.SetComplete -> {
                viewModelScope.launch {
                    taskUseCases.setCompleteTask(event.task)
                }
            }
            is TasksEvent.SetIncomplete -> {
                viewModelScope.launch {
                    taskUseCases.setIncompleteTask(event.task)
                }
            }
            is TasksEvent.Refresh -> {
                getTasks(TaskOrder.All)
            }
        }
    }

    private fun getTasks(taskOrder: TaskOrder){
        getTaskJob?.cancel()
        getTaskJob = taskUseCases.getTasks(taskOrder).onEach { tasks ->
            _uiState.value = uiState.value.copy(
                tasks = tasks,
                taskOrder = taskOrder
            )
        }.launchIn(viewModelScope)
    }
}