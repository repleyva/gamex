package com.repleyva.gamexapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreen
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreenState
import com.repleyva.gamexapp.presentation.ui.theme.GamexAppTheme
import org.junit.Rule
import org.junit.Test

class BookmarkScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun verifyBookmarkTitleAndDescription_displayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                BookmarkScreen(
                    state = BookmarkScreenState(),
                    onDetailScreen = {}
                )
            }
        }
        composeTestRule.onNodeWithText(context.getString(R.string.title_bookmark)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.label_bookmark_description)).assertIsDisplayed()
    }

    @Test
    fun verifyBookmark_whenIsNotEmpty() {
        composeTestRule.setContent {
            GamexAppTheme {
                BookmarkScreen(
                    BookmarkScreenState(games = sampleGames)
                ) {}
            }
        }

        composeTestRule.onNodeWithText(sampleGames[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleGames[1].name).assertIsDisplayed()
    }

    @Test
    fun verifyBookmark_whenGameIsClicked() {
        composeTestRule.setContent {
            GamexAppTheme {
                BookmarkScreen(
                    BookmarkScreenState(games = sampleGames)
                ) {
                    assert(it == sampleGames[0])
                }
            }
        }

        composeTestRule.onNodeWithText(sampleGames[0].name).performClick()
    }

    private val sampleGames = listOf(
        dummyGame.copy(1000L, name = "Game 1"),
        dummyGame.copy(1001L, name = "Game 2")
    )
}
