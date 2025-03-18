package com.example.memorygame.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memorygame.ui.theme.MemoryGameTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationsScreen(
    onItemClick: (Int) -> Unit // Обработчик клика по элементу списка
) {
    val configurations = remember { List(4) { "Configuration ${it + 1}" } } // Пример данных

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Toolbar (замените на ваш кастомный Toolbar)
        TopAppBar(
            title = { Text("Configurations") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Список конфигураций
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(configurations) { configuration ->
                ConfigurationItem(
                    text = configuration,
                    onClick = { onItemClick(configurations.indexOf(configuration)) }
                )
            }
        }
    }
}

@Composable
fun ConfigurationItem(
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Go to configuration"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigurationsScreenPreview() {
    MemoryGameTheme {
        ConfigurationsScreen(
            onItemClick = {  }
        )
    }
}