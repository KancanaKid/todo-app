package com.kancanakid.todo.app.features.presentation.add_edit_todo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kancanakid.todo.app.ui.theme.TodoAppTheme

/**
 * TextFields custom as we need transparent text field
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldTrailingIcon(
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    onClicked: () -> Unit,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            placeholderColor = Color.Black.copy(alpha = 0.5f),
            textColor = Color.Black
        ),
        placeholder = {
            if (isHintVisible) {
                Text(text = hint)
            }
        },
        singleLine = singleLine,
        trailingIcon = {
            IconButton(onClick = { onClicked() }) {
                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    singleLine: Boolean = false
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().onFocusChanged {
             onFocusChange(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            placeholderColor = Color.Black.copy(alpha = 0.5f),
            textColor = Color.Black
        ),

        placeholder = {
            if (isHintVisible) {
                Text(text = hint)
            }
        },
        singleLine = singleLine,
    )
}

@Composable
@Preview
private fun CustomTextFieldPreview() {
    TodoAppTheme {
        CustomTextField(
            text = "",
            hint = "Hint",
            onValueChange = {},
            singleLine = true,
            isHintVisible = false,
            onFocusChange = {}
        )
    }
}

@Composable
@Preview
private fun CustomTextFieldTrailingIconPreview() {
    TodoAppTheme {
        CustomTextFieldTrailingIcon(
            text = "",
            hint = "Hint",
            onValueChange = {},
            singleLine = true,
            onClicked = {},
            isHintVisible = false,
        )
    }
}

