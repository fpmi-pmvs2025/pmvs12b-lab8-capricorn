package com.example.memorygame.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.ui.theme.MemoryGameTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatsScreen(gameStatsList: List<Statistic>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        gameStatsList.forEach { gameStats ->
            StatCard(
                startTime = formatDate(gameStats.startTime),
                duration = formatDuration(gameStats.duration),
                numberOfCards = gameStats.numberOfCards,
                attempts = gameStats.attempts
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatCard(startTime: String, duration: String, numberOfCards: Int, attempts: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Start Time:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = Color.Gray
                )
                Text(
                    text = "Duration:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = Color.Gray
                )
                Text(
                    text = "Number of Cards:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = Color.Gray
                )
                Text(
                    text = "Attempts:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    color = Color.Gray
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = startTime,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black
                )
                Text(
                    text = numberOfCards.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black
                )
                Text(
                    text = attempts.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black
                )
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    MemoryGameTheme(
        dynamicColor = false
    ) {
        StatsScreen(
            gameStatsList = listOf(
                Statistic(duration = 300000, startTime = Date(), numberOfCards = 12, attempts = 3),
                Statistic(duration = 600000, startTime = Date(), numberOfCards = 24, attempts = 5)
            )
        )
    }
}
