package com.kancanakid.todo.app.di

import android.app.Application
import androidx.room.Room
import com.kancanakid.todo.app.features.data.datasource.TodoDatabase
import com.kancanakid.todo.app.features.data.repositories.TaskRepositoryImpl
import com.kancanakid.todo.app.features.domain.repositories.TaskRepository
import com.kancanakid.todo.app.features.domain.usecase.AddTaskUseCase
import com.kancanakid.todo.app.features.domain.usecase.DeleteTaskUseCase
import com.kancanakid.todo.app.features.domain.usecase.GetTaskUseCase
import com.kancanakid.todo.app.features.domain.usecase.GetTasksUseCase
import com.kancanakid.todo.app.features.domain.usecase.SetCompleteTaskUseCase
import com.kancanakid.todo.app.features.domain.usecase.SetIncompleteTaskUseCase
import com.kancanakid.todo.app.features.domain.usecase.TaskUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app:Application):TodoDatabase {
        return Room.databaseBuilder(app, TodoDatabase::class.java, TodoDatabase.DB_NAME).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db:TodoDatabase):TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository):TaskUseCases {
        return TaskUseCases(
            getTasks = GetTasksUseCase(repository),
            deleteTask = DeleteTaskUseCase(repository),
            addTask = AddTaskUseCase(repository),
            setCompleteTask = SetCompleteTaskUseCase(repository),
            getTask = GetTaskUseCase(repository),
            setIncompleteTask = SetIncompleteTaskUseCase(repository)
        )
    }
}