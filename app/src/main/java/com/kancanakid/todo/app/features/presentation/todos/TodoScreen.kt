package com.kancanakid.todo.app.features.presentation.todos

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kancanakid.todo.app.features.presentation.todos.components.OrderSection
import com.kancanakid.todo.app.features.presentation.todos.components.TaskItem
import com.kancanakid.todo.app.features.presentation.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoScreen(navController: NavController, viewModel: TasksViewModel = hiltViewModel()) {
    val state = viewModel.uiState.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val todoListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo App")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { viewModel.onEvent(TasksEvent.ToggleOrderSection) }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort Filter", tint = Color.White)
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditScreen.route) },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Box(modifier = Modifier.fillMaxWidth().background(color = Color.Gray.copy(alpha = 0.6f))){
                        OrderSection(
                            onOrderChange = {
                                viewModel.onEvent(TasksEvent.Order(it))
                            },
                            taskOrder = state.taskOrder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth(), state = todoListState) {
                    items(state.tasks) { task ->
                        TaskItem(
                            task = task,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate(Screen.AddEditScreen.route + "?taskId=${task.id}") },
                            onCheckChange = {
                                if(it){
                                    viewModel.onEvent(TasksEvent.SetComplete(task))
                                    scope.launch {
                                        snackBarHostState.showSnackbar("Task ${task.title} completed")
                                    }
                                }else{
                                    viewModel.onEvent(TasksEvent.SetIncomplete(task))
                                    scope.launch {
                                        snackBarHostState.showSnackbar("Task ${task.title} incomplete")
                                    }
                                }
                                viewModel.onEvent(TasksEvent.Refresh)
                            }
                        )
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        todoListState.scrollToItem(if(state.tasks.size > 1) state.tasks.size - 1 else 0)
                    }
                }
            }
        }
    )

}