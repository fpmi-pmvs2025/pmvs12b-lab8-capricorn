package com.example.memorygame.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memorygame.ui.theme.MemoryGameTheme

@Composable
fun MainScreen(
    onPlayClick: () -> Unit, // Обработчик для кнопки "Играть"
    onSettingsClick: () -> Unit, // Обработчик для кнопки "Настройки"
    onStatsClick: () -> Unit // Обработчик для кнопки "Статистика"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Кнопка "Играть"
        Button(
            onClick = onPlayClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Играть")
        }

        // Кнопка "Настройки"
        Button(
            onClick = onSettingsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Настройки")
        }

        // Кнопка "Статистика"
        Button(
            onClick = onStatsClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Статистика")
        }
    }
}