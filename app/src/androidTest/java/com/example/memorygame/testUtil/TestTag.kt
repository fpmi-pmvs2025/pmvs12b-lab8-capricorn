package com.example.memorygame.testUtil

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule

fun ComposeContentTestRule.onNodeWithTestTag(
    testTag: String,
    useUnmergedTree: Boolean = false
) = onNode(
    SemanticsMatcher.expectValue(SemanticsProperties.TestTag, testTag),
    useUnmergedTree
)

fun SemanticsMatcher.Companion.hasTestTag(testTag: String): SemanticsMatcher =
    expectValue(SemanticsProperties.TestTag, testTag)