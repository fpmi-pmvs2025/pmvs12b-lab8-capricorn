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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.bumptech.glide.request.RequestOptions
import com.example.memorygame.ui.theme.MemoryGameTheme
import com.example.memorygame.PlayViewModel
import com.example.memorygame.R
import com.example.memorygame.util.formatDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlayScreen(
    numberOfCards: Int,
    onNewGameClick: () -> Unit,
    onFabClick: () -> Unit,
    viewModel: PlayViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val cards by viewModel.cards.collectAsState()
    val openedCards = remember { mutableStateListOf<Int>() }
    val matchedCards = remember { mutableStateListOf<Int>() }
    val rotatedCards = remember { mutableStateMapOf<Int, Boolean>() }
    val isLoading = remember { mutableStateOf(true) }
    val startTime = remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        isLoading.value = true
        viewModel.fetchCards(numberOfCards)
        isLoading.value = false
        startTime.value = Date() // Record the start time when the game begins
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading.value) {
            // Show loading indicator
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                val duration = System.currentTimeMillis() - startTime.value.time
                val matchedPairs = matchedCards.size / 2
                val totalAttempts = openedCards.size / 2

                StatCard(
                    duration = formatDuration(duration),
                    matchedPairs = matchedPairs,
                    totalAttempts = totalAttempts
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cards) { card ->
                        FlipCard(
                            card = card,
                            isMatched = matchedCards.contains(card.id),
                            isOpened = openedCards.contains(card.id),
                            isRotated = rotatedCards[card.id] ?: false,
                            onClick = {
                                if (openedCards.size < 2 && !openedCards.contains(card.id) && !matchedCards.contains(card.id)) {
                                    rotatedCards[card.id] = true
                                    openedCards.add(card.id)

                                    if (openedCards.size == 2) {
                                        scope.launch {
                                            delay(1000)
                                            val firstCard = cards.find { it.id == openedCards[0] }
                                            val secondCard = cards.find { it.id == openedCards[1] }

                                            if (firstCard?.value == secondCard?.value) {
                                                matchedCards.addAll(openedCards)
                                            } else {
                                                openedCards.forEach { rotatedCards[it] = false }
                                            }
                                            openedCards.clear()
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        /*FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 25.dp, bottom = 40.dp),
            containerColor = Color.Green,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Start Game")
        }*/
    }
}

@Composable
fun FlipCard(
    card: CardData,
    isMatched: Boolean,
    isOpened: Boolean,
    isRotated: Boolean,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
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
fun StatCard(duration: String, matchedPairs: Int, totalAttempts: Int) {
    Card(
        modifier = Modifier
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.matched_pairs_label),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.time_label),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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

@Preview(showBackground = true)
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
}
