package com.example.memorygame.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memorygame.R
import com.example.memorygame.ui.theme.MemoryGameTheme

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            SettingsCategory(title = stringResource(R.string.appearance_category)) {
                ThemeSettingsCard()
                Spacer(modifier = Modifier.height(12.dp))
            }

            SettingsCategory(title = stringResource(R.string.language_category)) {
                LanguageSettingsCard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSettingsCard() {
    val options = listOf(
        stringResource(R.string.english),
        stringResource(R.string.russian)
    )
    var expanded by remember { mutableStateOf(false) }
    val textFieldState = rememberTextFieldState(options[0])

    SettingItem(
        icon = ImageVector.vectorResource(R.drawable.translate_24px),
        title = stringResource(R.string.language_title),
        description = stringResource(R.string.language_desc)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                state = textFieldState,
                readOnly = true,
                lineLimits = TextFieldLineLimits.SingleLine,
                label = { Text(stringResource(R.string.select_language)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            textFieldState.setTextAndPlaceCursorAtEnd(option)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeSettingsCard() {
    var darkThemeEnabled by remember { mutableStateOf(false) }

    SettingItem(
        icon = ImageVector.vectorResource(R.drawable.dark_mode_24px),
        title = stringResource(R.string.dark_theme_title),
        description = stringResource(R.string.dark_theme_desc)
    ) {
        Switch(
            checked = darkThemeEnabled,
            onCheckedChange = { darkThemeEnabled = it }
        )
    }
}

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    description: String,
    compact: Boolean = false,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (compact) 64.dp else 72.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(20.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        content()
    }
}

@Composable
fun SettingsCategory(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    MemoryGameTheme(
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SettingsScreen()
        }
    }
}