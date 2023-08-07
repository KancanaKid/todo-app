package com.kancanakid.todo.app.features.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Task entity as task table requirement.
 * @param id as primary key
 * @param title as task title
 * @param dueDate as task duedate
 * @param description as task description
 * @param isCompleted as complete/incomplete task state
 * @exception InvalidTaskException as checking user input validation
 */
@Entity
data class Task(
    @PrimaryKey
    val id:Int? = null,
    val title:String,
    @ColumnInfo(name = "due_date")
    val dueDate:Long,
    val description:String,
    @ColumnInfo(name = "is_completed")
    val isCompleted:Boolean = false
)

class InvalidTaskException(message:String):Exception(message)
