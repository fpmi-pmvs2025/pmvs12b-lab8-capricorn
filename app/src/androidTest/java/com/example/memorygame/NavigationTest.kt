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
    fun appNavigation_mainToPlayFlow() {
        composeTestRule.onNodeWithTag("play_button").assertExists()

        composeTestRule.onNodeWithTag("play_button").performClick()

        composeTestRule.onNodeWithTag("config_item_4").assertExists()

        composeTestRule.onNodeWithTag("config_item_12").performClick()

        composeTestRule.onNodeWithTag("play_screen_top_bar").assertExists()

        composeTestRule.waitUntil(Long.MAX_VALUE) {
            (0..11).all { i ->
                composeTestRule.onAllNodesWithTag("card_$i").fetchSemanticsNodes().isNotEmpty()
            }
        }

        for (i in 0..11) {
            composeTestRule.onNodeWithTag("card_$i").assertExists()
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