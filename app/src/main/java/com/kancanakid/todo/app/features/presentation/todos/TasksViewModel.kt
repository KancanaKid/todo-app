package com.kancanakid.todo.app.features.presentation.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kancanakid.todo.app.features.domain.model.Task
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

    private val _uiState = mutableStateOf<TasksState>(TasksState())
    val uiState: State<TasksState> = _uiState

    // to flag the last task item deleted
    private var recentlyDeletedTask:Task? = null

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

            }
            is TasksEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }
            }
            is TasksEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.addTask(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }
            }
            is TasksEvent.ToggleOrderSection -> {
                _uiState.value = uiState.value.copy(
                    isOrderSectionVisible = !uiState.value.isOrderSectionVisible
                )

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