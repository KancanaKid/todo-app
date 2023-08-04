package com.kancanakid.todo.app.features.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kancanakid.todo.app.features.domain.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val taskDao:TaskDao

    companion object {
        const val DB_NAME = "todo.db"
    }
}