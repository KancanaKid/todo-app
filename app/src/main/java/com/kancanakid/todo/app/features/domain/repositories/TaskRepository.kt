package com.kancanakid.todo.app.features.domain.repositories

import com.kancanakid.todo.app.features.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks() : Flow<List<Task>>

    suspend fun getTaskById(id:Int) : Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateCompleted(id: Int, completed:Boolean)

    suspend fun deleteCompletedTask()
}