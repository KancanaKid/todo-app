package com.kancanakid.todo.app.features.presentation.add_edit_todo.components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import java.util.Calendar
import java.util.Date

@Composable
fun CalendarPickerDialog(
    context: Context, selectedDate: (Date) -> Unit,
    calendar:Calendar = Calendar.getInstance()
) {
    DatePickerDialog(
        context, {_, year, month, day ->
            calendar.set(year, month, day)
            selectedDate(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}