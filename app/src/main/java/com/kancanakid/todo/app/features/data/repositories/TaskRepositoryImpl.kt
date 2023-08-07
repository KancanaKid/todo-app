package com.kancanakid.todo.app.features.data.repositories

import com.kancanakid.todo.app.features.data.datasource.TaskDao
import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.features.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
/**
 * Repository implementation from TaskRepository abstract / interface
 */
class TaskRepositoryImpl( private val taskDao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTask()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateCompleted(id: Int, completed: Boolean) {
        taskDao.updateCompleted(id, completed)
    }

    override suspend fun deleteCompletedTask() {
        taskDao.deleteCompletedTasks()
    }
}