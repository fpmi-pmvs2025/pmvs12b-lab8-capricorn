package com.example.memorygame.Screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorygame.ui.theme.MemoryGameTheme

@Composable
fun PlayScreen(
    onNewGameClick: () -> Unit, // Обработчик для кнопки "New Game"
    onFabClick: () -> Unit // Обработчик для FloatingActionButton
) {
    val gameFinished by remember { mutableStateOf(false) } // Состояние завершения игры
    val cards = remember { List(16) { it } } // Пример данных для карточек

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Карточка для статистики
            StatCard(
                won = 0,
                failures = 0
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Сетка карточек
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 колонки
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cards) { card ->
                    CardItem(card = card)
                }
            }
        }

        // FloatingActionButton
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

        // Сообщение о завершении игры
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

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onNewGameClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "New Game",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
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

@Composable
fun CardItem(card: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Квадратная карточка
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Card $card",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayPreview() {
    MemoryGameTheme {
        PlayScreen(
            onNewGameClick = {},
            onFabClick = {}
        )
    }
}