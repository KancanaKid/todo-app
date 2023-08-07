package com.kancanakid.todo.app.features.presentation.add_edit_todo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kancanakid.todo.app.features.presentation.add_edit_todo.components.CalendarPickerDialog
import com.kancanakid.todo.app.features.presentation.add_edit_todo.components.CustomTextField
import com.kancanakid.todo.app.features.presentation.add_edit_todo.components.CustomTextFieldTrailingIcon
import com.kancanakid.todo.app.ui.theme.TodoAppTheme
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val titleState = viewModel.taskTitle.value
    val descriptionState = viewModel.taskDescription.value
    val dueDateState = viewModel.taskDueDate.value

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val context = LocalContext.current
    var isCalendarDialogShown by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }

                is UiEvent.SaveTask -> {
                    navController.navigateUp()
                }

                is UiEvent.DeleteTask -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (titleState.text.isNotBlank()) "Edit Task" else "Add Task")
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(AddEditTaskEvent.DeleteTask) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTaskEvent.SaveTask)
            }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Task")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },

    ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isCalendarDialogShown) {
                CalendarPickerDialog(context = context, selectedDate = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredDueDate(viewModel.format.format(it)))
                    viewModel.onEvent(AddEditTaskEvent.CurrentSelectedDate(it.time))
                    isCalendarDialogShown = false
                })
            }
            CustomTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTaskEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
            )
            CustomTextFieldTrailingIcon(
                text = dueDateState.text,
                hint = dueDateState.hint,
                singleLine = true,
                isHintVisible = dueDateState.isHintVisible,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredDueDate(it))
                },
                onClicked = {
                    isCalendarDialogShown = true
                }
            )
            CustomTextField(
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTaskEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                singleLine = false,
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TodoAppTheme {
        AddEditTaskScreen(navController = rememberNavController())
    }
}