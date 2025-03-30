package com.example.memorygame.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memorygame.PlayViewModel
import com.example.memorygame.R
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.ui.theme.MemoryGameTheme
import com.example.memorygame.util.formatDate
import com.example.memorygame.util.formatDuration
import java.util.Date

@Composable
fun StatsScreen(
    onBackClick: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val gameStatsList by viewModel.gameStatsList.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Заголовок
            Text(
                text = stringResource(R.string.stats_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (gameStatsList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_stats_data),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
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
    }
}

@Composable
fun StatCard(startTime: String, duration: String, numberOfCards: Int, attempts: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    text = stringResource(R.string.start_time),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.duration),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.cards_count),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.attempts),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = startTime)
                Text(text = duration)
                Text(text = numberOfCards.toString())
                Text(text = attempts.toString())
            }
        }
    }
}