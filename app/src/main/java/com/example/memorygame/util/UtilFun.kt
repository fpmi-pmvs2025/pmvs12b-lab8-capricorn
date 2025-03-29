package com.example.memorygame.util

import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

@Composable
fun formatDuration(duration: Long): String {
    val minutes = duration / 60000
    val seconds = (duration % 60000) / 1000
    return String.format("%d min %d sec", minutes, seconds)
}