@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memorygame.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Карточка для настройки звука
        //SoundSettingsCard()

        Spacer(modifier = Modifier.height(16.dp))

        // Карточка для выбора языка
        LanguageSettingsCard()
    }
}

@Composable
fun SoundSettingsCard() {
    var soundState by remember { mutableStateOf(true) } // Состояние для RadioButton

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Sound",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Группа RadioButton
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                RadioButton(
                    selected = soundState,
                    onClick = { soundState = true },
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Green)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "On",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = !soundState,
                    onClick = { soundState = false },
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Green)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Off",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun LanguageSettingsCard() {
    val languages = listOf("English", "Русский")
    var selectedLanguage by remember { mutableStateOf(languages[0]) } // Состояние для Spinner

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Language",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Выпадающий список (Spinner)
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = false, // Управление состоянием раскрытия
                onExpandedChange = {}
            ) {
                TextField(
                    value = selectedLanguage,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = {}
                ) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(text = language) },
                            onClick = {
                                selectedLanguage = language
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    MemoryGameTheme {
        SettingsScreen()
    }
}