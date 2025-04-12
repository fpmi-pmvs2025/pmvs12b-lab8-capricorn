package com.example.memorygame

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memorygame.R
import com.example.memorygame.screens.MainActivity
import com.example.memorygame.screens.PlayScreen
import com.example.memorygame.ui.theme.MemoryGameTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                PlayScreen(
                    numberOfCards = 12,
                    onNewGameClick = {},
                    onBackClick = {}
                )
            }
        }
    }

    @Test
    fun testTopAppBarDisplayed() {
        composeTestRule.onNodeWithTag("play_screen_top_bar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("play_screen_back_button").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClick() {
        composeTestRule.onNodeWithTag("play_screen_back_button").performClick()
    }

    @Test
    fun testStatCardDisplayed() {
        composeTestRule.onNodeWithTag("stat_card").assertIsDisplayed()
    }
}