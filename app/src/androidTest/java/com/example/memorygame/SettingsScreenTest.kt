package com.example.memorygame

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.memorygame.R
import com.example.memorygame.screens.MainActivity
import com.example.memorygame.screens.SettingsScreen
import com.example.memorygame.testUtil.onNodeWithTestTag
import com.example.memorygame.ui.theme.MemoryGameTheme
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun settingsScreen_verifyInitialState() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                SettingsScreen(onBackClick = {})
            }
        }

        composeTestRule.onNodeWithTag("settings_top_bar").assertExists()
        composeTestRule.onNodeWithTag("theme_settings_card").assertExists()
        composeTestRule.onNodeWithTag("theme_selector").assertExists()
    }

    @Test
    fun settingsScreen_backButton_invokesCallback() {
        var backClicked = false

        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                SettingsScreen(onBackClick = { backClicked = true })
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.back)
        ).performClick()
        assert(backClicked)
    }

    @Test
    fun settingsScreen_themeSelection_works() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                SettingsScreen(onBackClick = {})
            }
        }

        composeTestRule.onNodeWithTag("theme_selector").performClick()

        composeTestRule.onNodeWithTag("theme_option_light").assertExists()
        composeTestRule.onNodeWithTag("theme_option_dark").assertExists()

        composeTestRule.onNodeWithTag("theme_option_dark").performClick()

        composeTestRule.onNodeWithTag("theme_selector").performClick()
        composeTestRule.onNodeWithTag("theme_option_dark").assertExists()
    }
}