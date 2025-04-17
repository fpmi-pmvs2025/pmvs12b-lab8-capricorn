package com.example.memorygame

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.example.memorygame.presentation.ui.screens.ConfigurationScreen
import com.example.memorygame.presentation.ui.theme.MemoryGameTheme
import com.example.memorygame.testUtil.onNodeWithTestTag
import org.junit.Rule
import org.junit.Test

class ConfigurationScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun configurationScreen_verifyOptionsExist() {
        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                ConfigurationScreen(
                    onConfigurationSelected = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTestTag("config_item_4").assertExists()
        composeTestRule.onNodeWithTestTag("config_item_6").assertExists()
        composeTestRule.onNodeWithTestTag("config_item_12").assertExists()
        composeTestRule.onNodeWithTestTag("config_item_24").assertExists()
    }

    @Test
    fun configurationScreen_clickOption_invokesCallback() {
        var selectedCards = 0

        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                ConfigurationScreen(
                    onConfigurationSelected = { cards -> selectedCards = cards },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTestTag("config_item_12").performClick()
        assert(selectedCards == 12)
    }

    @Test
    fun configurationScreen_backButton_invokesCallback() {
        var backClicked = false

        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                ConfigurationScreen(
                    onConfigurationSelected = {},
                    onBackClick = { backClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithTestTag("config_screen_back_button").performClick()
        assert(backClicked)
    }
}