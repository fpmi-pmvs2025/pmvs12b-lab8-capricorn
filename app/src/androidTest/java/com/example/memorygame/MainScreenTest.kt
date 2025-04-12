package com.example.memorygame

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.example.memorygame.screens.MainScreen
import com.example.memorygame.testUtil.onNodeWithTestTag
import com.example.memorygame.ui.theme.MemoryGameTheme
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreen_verifyButtonsExist() {
        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                MainScreen(
                    onPlayClick = {},
                    onSettingsClick = {},
                    onStatsClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTestTag("play_button").assertExists()
        composeTestRule.onNodeWithTestTag("settings_button").assertExists()
        composeTestRule.onNodeWithTestTag("stats_button").assertExists()
    }

    @Test
    fun mainScreen_clickPlayButton_invokesCallback() {
        var playClicked = false

        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                MainScreen(
                    onPlayClick = { playClicked = true },
                    onSettingsClick = {},
                    onStatsClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTestTag("play_button").performClick()
        assert(playClicked)
    }

    @Test
    fun mainScreen_clickSettingsButton_invokesCallback() {
        var settingsClicked = false

        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                MainScreen(
                    onPlayClick = {},
                    onSettingsClick = { settingsClicked = true },
                    onStatsClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTestTag("settings_button").performClick()
        assert(settingsClicked)
    }

    @Test
    fun mainScreen_clickStatsButton_invokesCallback() {
        var statsClicked = false

        composeTestRule.setContent {
            MemoryGameTheme(darkTheme = false) {
                MainScreen(
                    onPlayClick = {},
                    onSettingsClick = {},
                    onStatsClick = { statsClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithTestTag("stats_button").performClick()
        assert(statsClicked)
    }
}