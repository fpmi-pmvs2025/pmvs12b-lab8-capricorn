package com.example.memorygame.Screens

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.ui.theme.MemoryGameTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayScreen(
    numberOfCards: Int,
    onNewGameClick: () -> Unit,
    onFabClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val cards = remember { generateCardPairs(numberOfCards) }
    val openedCards = remember { mutableStateListOf<Int>() }
    val matchedCards = remember { mutableStateListOf<Int>() }
    val gameFinished = matchedCards.size == cards.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StatCard(
                won = matchedCards.size / 2,
                failures = openedCards.size / 2 - matchedCards.size / 2
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
                        onClick = {
                            if (openedCards.size < 2 && !openedCards.contains(card.id)) {
                                openedCards.add(card.id)
                                if (openedCards.size == 2) {
                                    scope.launch {
                                        delay(1000) // Задержка для проверки совпадения
                                        val firstCard = cards.find { it.id == openedCards[0] }
                                        val secondCard = cards.find { it.id == openedCards[1] }
                                        if (firstCard?.value == secondCard?.value) {
                                            matchedCards.addAll(openedCards)
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

        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 25.dp, bottom = 40.dp),
            containerColor = Color.Green,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Start Game")
        }

        if (gameFinished) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Finished",
                    color = Color.Green,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun StatCard(won: Int, failures: Int) {
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
            Text(
                text = "Success: $won",
                color = Color.Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Failures: $failures",
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

data class CardData(
    val id: Int,
    val value: Int
)

fun generateCardPairs(numberOfCards: Int): List<CardData> {
    val values = List(numberOfCards / 2) { it + 1 }
    val pairs = values + values
    return pairs.shuffled().mapIndexed { index, value -> CardData(index, value) }
}

@Composable
fun FlipCard(
    card: CardData,
    isMatched: Boolean,
    isOpened: Boolean,
    onClick: () -> Unit
) {
    // Состояние для анимации переворота
    val rotation = animateFloatAsState(
        targetValue = if (isOpened || isMatched) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    // Состояние для определения, видна ли лицевая сторона карточки
    val isFrontVisible = rotation.value > 90f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .clickable(enabled = !isMatched) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Обратная сторона карточки (видна, если карточка не открыта или не совпала)
        if (!isFrontVisible) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation.value),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Лицевая сторона карточки (видна, если карточка открыта или совпала)
        if (isFrontVisible) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation.value + 180f),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Green)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.value.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayPreview() {
    MemoryGameTheme {
        PlayScreen(
            12,
            onNewGameClick = {},
            onFabClick = {}
        )
    }
}