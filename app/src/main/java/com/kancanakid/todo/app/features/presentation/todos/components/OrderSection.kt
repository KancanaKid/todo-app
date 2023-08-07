package com.kancanakid.todo.app.features.presentation.todos.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kancanakid.todo.app.features.domain.util.TaskOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    taskOrder: TaskOrder,
    onOrderChange: (TaskOrder) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        FilterRadioButton(
            label = "All",
            selected = taskOrder is TaskOrder.All,
            onSelect = { onOrderChange(TaskOrder.All) })
        Spacer(modifier = Modifier.width(5.dp))
        FilterRadioButton(
            label = "Active",
            selected = taskOrder is TaskOrder.Active,
            onSelect = { onOrderChange(TaskOrder.Active) })
        Spacer(modifier = Modifier.width(5.dp))
        FilterRadioButton(
            label = "Completed",
            selected = taskOrder is TaskOrder.Completed,
            onSelect = { onOrderChange(TaskOrder.Completed) })
    }
}