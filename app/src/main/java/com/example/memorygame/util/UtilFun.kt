package com.example.memorygame.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.memorygame.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(
        stringResource(R.string.date_format),
        Locale.getDefault()
    )
    return dateFormat.format(date)
}

@Composable
fun formatDuration(duration: Long): String {
    val minutes = duration / 60000
    val seconds = (duration % 60000) / 1000
    return stringResource(
        R.string.duration_format,
        minutes,
        seconds
    )
}