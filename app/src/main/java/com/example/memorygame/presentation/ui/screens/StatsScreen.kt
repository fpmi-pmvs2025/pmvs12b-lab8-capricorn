package com.example.memorygame.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memorygame.R
import com.example.memorygame.presentation.PlayViewModel
import com.example.memorygame.util.formatDate
import com.example.memorygame.util.formatDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    onBackClick: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val gameStatsList by viewModel.gameStatsList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("stats_top_bar"),
                title = { Text(stringResource(R.string.stats_title),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()) },
                    navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                if (gameStatsList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_stats_data),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(gameStatsList) { gameStats ->
                            StatCard(
                                startTime = formatDate(gameStats.startTime),
                                duration = formatDuration(gameStats.duration),
                                numberOfCards = gameStats.numberOfCards,
                                attempts = gameStats.attempts,
                                modifier = Modifier.testTag("stat_item_${gameStats.startTime.time}")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(startTime: String, duration: String, numberOfCards: Int, attempts: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.secondary
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
