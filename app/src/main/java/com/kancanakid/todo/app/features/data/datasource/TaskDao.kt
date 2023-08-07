package com.kancanakid.todo.app.features.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kancanakid.todo.app.features.domain.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the task table.
 *
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTask():Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId:Int):Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE task SET is_completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)

    @Query("DELETE FROM task WHERE is_completed = 1")
    suspend fun deleteCompletedTasks(): Int

}