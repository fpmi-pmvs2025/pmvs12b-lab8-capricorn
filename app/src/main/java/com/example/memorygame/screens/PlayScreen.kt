package com.example.memorygame.screens

import android.widget.ImageView
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.example.memorygame.ui.theme.MemoryGameTheme
import com.example.memorygame.PlayViewModel
import com.example.memorygame.R
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.util.formatDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(
    numberOfCards: Int,
    onNewGameClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val cards by viewModel.cards.collectAsState()
    val openedCards = remember { mutableStateListOf<Int>() }
    val matchedCards = remember { mutableStateListOf<Int>() }
    val rotatedCards = remember { mutableStateMapOf<Int, Boolean>() }
    val isLoading = remember { mutableStateOf(true) }
    val startTime = remember { mutableStateOf(Date()) }
    val totalFlips = remember { mutableStateOf(0) }
    val matchedPairs = remember { mutableStateOf(0) }
    val isGameCompleted = matchedPairs.value == numberOfCards / 2
    val showCompletionDialog = remember { mutableStateOf(false) }

    // Определяем количество колонок в зависимости от количества карт
    val columns = when {
        numberOfCards <= 6 -> 2
        numberOfCards <= 12 -> 3
        numberOfCards <= 20 -> 4
        else -> 5
    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        viewModel.fetchCards(numberOfCards)
        isLoading.value = false
        startTime.value = Date()
    }

    LaunchedEffect(isGameCompleted) {
        if (isGameCompleted && !isLoading.value) {
            val gameStats = Statistic(
                duration = System.currentTimeMillis() - startTime.value.time,
                startTime = startTime.value,
                numberOfCards = numberOfCards,
                attempts = totalFlips.value
            )
            viewModel.saveGameResult(gameStats)
            showCompletionDialog.value = true
        }
    }

    if (showCompletionDialog.value) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = { Text(stringResource(R.string.congratulations)) },
            text = { Text(stringResource(R.string.game_completed)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCompletionDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("play_screen_top_bar"),
                title = {
                        Text(
                            text = stringResource(R.string.play_title),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("play_screen_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val duration = System.currentTimeMillis() - startTime.value.time

                    StatCard(
                        duration = formatDuration(duration),
                        matchedPairs = matchedPairs.value,
                        totalAttempts = totalFlips.value,
                        modifier = Modifier.testTag("stat_card")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cards) { card ->
                            FlipCard(
                                modifier = Modifier.testTag("card_${card.id}"),
                                card = card,
                                isMatched = matchedCards.contains(card.id),
                                isOpened = openedCards.contains(card.id),
                                isRotated = rotatedCards[card.id] ?: false,
                                onClick = {
                                    if (openedCards.size < 2 && !openedCards.contains(card.id) && !matchedCards.contains(card.id)) {
                                        totalFlips.value++
                                        rotatedCards[card.id] = true
                                        openedCards.add(card.id)

                                        if (openedCards.size == 2) {
                                            scope.launch {
                                                delay(1000)
                                                val firstCard = cards.find { it.id == openedCards[0] }
                                                val secondCard = cards.find { it.id == openedCards[1] }

                                                if (firstCard?.value == secondCard?.value) {
                                                    matchedCards.addAll(openedCards)
                                                    matchedPairs.value++
                                                } else {
                                                    openedCards.forEach { rotatedCards[it] = false }
                                                }
                                                openedCards.clear()
                                            }
                                        }
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FlipCard(
    card: CardData,
    isMatched: Boolean,
    isOpened: Boolean,
    isRotated: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(500)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable(
                enabled = !isMatched,
                onClick = onClick
            )
    ) {
        // Обратная сторона карточки (рубашка)
        if (rotation < 90f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 1f - rotation / 180f
                    },
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        android.widget.ImageView(context).apply {
                            scaleType = ImageView.ScaleType.FIT_CENTER
                            adjustViewBounds = true
                            Glide.with(context)
                                .load("https://deckofcardsapi.com/static/img/back.png")
                                .into(this)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Лицевая сторона карточки
        if (rotation > 90f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = (rotation / 180f)
                        rotationY = 180f
                    },
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        android.widget.ImageView(context).apply {
                            scaleType = ImageView.ScaleType.FIT_CENTER
                            adjustViewBounds = true
                            Glide.with(context)
                                .load(card.image)
                                .into(this)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun StatCard(duration: String, matchedPairs: Int, totalAttempts: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Строка с подписями
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.attempts_label),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.matched_pairs_label),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.time_label),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Строка со значениями
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = totalAttempts.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = matchedPairs.toString(),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = duration,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

data class CardData(
    val id: Int,
    val value: String,
    val image: String
)

/*@Preview(showBackground = true)
@Composable
fun PlayPreview() {
    MemoryGameTheme(
        dynamicColor = false
    ) {
        PlayScreen(
            12,
            onNewGameClick = {},
            onFabClick = {}
        )
    }
}*/
