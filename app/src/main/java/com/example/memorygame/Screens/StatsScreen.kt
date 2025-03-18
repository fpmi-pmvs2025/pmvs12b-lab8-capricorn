package com.example.memorygame.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.graphics.Color

import androidx.compose.material3.CardDefaults
import androidx.compose.ui.tooling.preview.Preview
import com.example.memorygame.MyApp
import com.example.memorygame.ui.theme.MemoryGameTheme

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Карточка для "Won"
        StatCard(
            label = "Won",
            value = "10",
            valueColor = Color.Green
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Карточка для "Lose"
        StatCard(
            label = "Lose",
            value = "3",
            valueColor = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Карточка для "Attempts"
        StatCard(
            label = "Attempts",
            value = "3",
            valueColor = Color.Gray
        )
    }
}

@Composable
fun StatCard(label: String, value: String, valueColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp) // Исправлено здесь
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = Color.Black
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = valueColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    MemoryGameTheme {
        StatsScreen()
    }
}