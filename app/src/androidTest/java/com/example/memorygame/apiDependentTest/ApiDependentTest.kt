package com.example.memorygame.apiDependentTest

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memorygame.MyApp
import com.example.memorygame.screens.MainActivity
import com.example.memorygame.screens.PlayScreen
import com.example.memorygame.ui.theme.MemoryGameTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApiDependentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCardsDisplayed() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                PlayScreen(
                    numberOfCards = 12,
                    onNewGameClick = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule.waitUntil(1000000) {
            (0..11).all { i ->
                composeTestRule.onAllNodesWithTag("card_$i").fetchSemanticsNodes().isNotEmpty()
            }
        }

        for (i in 0..11) {
            composeTestRule.onNodeWithTag("card_$i").assertIsDisplayed()
        }
    }

    @Test
    fun testCardClick() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                PlayScreen(
                    numberOfCards = 12,
                    onNewGameClick = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule.waitUntil(1000000) {
            (0..11).all { i ->
                composeTestRule.onAllNodesWithTag("card_$i").fetchSemanticsNodes().isNotEmpty()
            }
        }

        composeTestRule.onNodeWithTag("card_0").performClick()
        composeTestRule.onNodeWithTag("card_11").performClick()
    }

    @Test
    fun appNavigation_mainToPlayFlow() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                MyApp()
            }
        }

        composeTestRule.onNodeWithTag("play_button").assertExists()

        composeTestRule.onNodeWithTag("play_button").performClick()

        composeTestRule.onNodeWithTag("config_item_4").assertExists()

        composeTestRule.onNodeWithTag("config_item_12").performClick()

        composeTestRule.onNodeWithTag("play_screen_top_bar").assertExists()

        composeTestRule.waitUntil(1000000) {
            (0..11).all { i ->
                composeTestRule.onAllNodesWithTag("card_$i").fetchSemanticsNodes().isNotEmpty()
            }
        }

        for (i in 0..11) {
            composeTestRule.onNodeWithTag("card_$i").assertExists()
        }
    }
}