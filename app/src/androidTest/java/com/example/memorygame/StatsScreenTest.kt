package com.example.memorygame

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.memorygame.presentation.ui.screens.MainActivity
import com.example.memorygame.presentation.ui.theme.MemoryGameTheme
import com.example.memorygame.screens.StatsScreen
import com.example.memorygame.testUtil.onNodeWithTestTag
import org.junit.Rule
import org.junit.Test

class StatsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun statsScreen_verifyInitialState() {
        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                StatsScreen(onBackClick = {})
            }
        }

        composeTestRule.onNodeWithTestTag("stats_top_bar").assertExists()
    }

    @Test
    fun statsScreen_backButton_invokesCallback() {
        var backClicked = false

        composeTestRule.activity.setContent {
            MemoryGameTheme(darkTheme = false) {
                StatsScreen(onBackClick = { backClicked = true })
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.back)
        ).performClick()
        assert(backClicked)
    }
}