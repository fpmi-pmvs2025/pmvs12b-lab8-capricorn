package com.example.memorygame

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.memorygame.screens.MainActivity
import com.example.memorygame.screens.PlayScreen
import com.example.memorygame.testUtil.onNodeWithTestTag
import com.example.memorygame.ui.theme.MemoryGameTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                MyApp()
            }
        }
    }

    @Test
    fun appNavigation_mainToSettings() {
        composeTestRule.onNodeWithTestTag("settings_button").assertExists()

        composeTestRule.onNodeWithTestTag("settings_button").performClick()

        composeTestRule.onNodeWithTestTag("settings_top_bar").assertExists()
        composeTestRule.onNodeWithTestTag("theme_settings_card").assertExists()
    }

    @Test
    fun appNavigation_mainToStats() {
        composeTestRule.onNodeWithTestTag("stats_button").assertExists()

        composeTestRule.onNodeWithTestTag("stats_button").performClick()

        composeTestRule.onNodeWithTestTag("stats_top_bar").assertExists()
    }
}