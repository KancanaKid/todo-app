package com.kancanakid.todo.app.features.presentation.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kancanakid.todo.app.features.domain.model.Task
import com.kancanakid.todo.app.ui.theme.TodoAppTheme

/**
 * List item decoration. It showing an item with checkbox to set a task either complete or incomplete
 */

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckChange: (Boolean) -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = task.isCompleted, onCheckedChange = onCheckChange)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (task.isCompleted) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
            }
        }
    }

}

@Composable
@Preview
fun TaskItemPreview() {
    TodoAppTheme {
        TaskItem(
            task = Task(0, "Title", 11111111, "Testing", true),
            onCheckChange = {})
    }
}