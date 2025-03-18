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
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Кнопка "Играть"
        Button(
            onClick = { navController.navigate("play") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Играть", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Настройки"
        Button(
            onClick = { navController.navigate("settings") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Настройки", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Статистика"
        Button(
            onClick = { navController.navigate("stats") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Статистика", fontSize = 18.sp)
        }
    }
}
