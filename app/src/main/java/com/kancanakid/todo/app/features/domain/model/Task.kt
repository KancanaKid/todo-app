package com.kancanakid.todo.app.features.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey
    val id:Int? = null,
    val title:String,
    @ColumnInfo(name = "due_date")
    val dueDate:Long,
    val description:String,
    @ColumnInfo(name = "is_completed")
    val isCompleted:Boolean
)
