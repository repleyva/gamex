package com.repleyva.gamexapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.search.SearchScreen
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.OnSearchQueryChange
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenState
import com.repleyva.gamexapp.presentation.ui.theme.GamexAppTheme
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun verifySearchTitle_displayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(),
                    eventHandler = {},
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_search_title)).assertIsDisplayed()
    }

    @Test
    fun verifySearchPlaceholder_displayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(),
                    eventHandler = {},
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_search_placeholder)).assertIsDisplayed()
    }

    @Test
    fun verifySearchIcon_displayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(),
                    eventHandler = {},
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("searchIcon").assertIsDisplayed()
    }

    @Test
    fun verifySendIcon_displayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(),
                    eventHandler = {},
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("sendIcon").assertIsDisplayed()
    }

    @Test
    fun verifyPlaceholder_whenQuery_inNotEmpty() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(),
                    eventHandler = { assert(it is OnSearchQueryChange) },
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("searchInput").performTextInput("test")
        composeTestRule.onNodeWithTag("placeholderText").assertDoesNotExist()
    }

    @Test
    fun verifySearchResults_displayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(games = sampleGames),
                    eventHandler = {},
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithText(sampleGames[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleGames[1].name).assertIsDisplayed()
    }

    @Test
    fun verifySearchResults_whenGameIsClicked() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(games = sampleGames),
                    eventHandler = {},
                    onDetailScreen = { assert(it == sampleGames[0]) }
                )
            }
        }

        composeTestRule.onNodeWithText(sampleGames[0].name).performClick()
    }

    @Test
    fun verifySearchResults_whenListIsLarge() {
        composeTestRule.setContent {
            GamexAppTheme {
                SearchScreen(
                    state = SearchScreenState(games = sampleLargeGames),
                    eventHandler = {},
                    onDetailScreen = {}
                )
            }
        }

        composeTestRule.onNodeWithText(sampleLargeGames[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleLargeGames[1].name).assertIsDisplayed()
        composeTestRule.onNodeWithTag("searchResultList").performScrollToIndex(sampleLargeGames.lastIndex)
        composeTestRule.onNodeWithText(sampleLargeGames[sampleLargeGames.lastIndex].name).assertIsDisplayed()
    }

    private val sampleGames = listOf(
        dummyGame.copy(1000L, name = "Game 1"),
        dummyGame.copy(1001L, name = "Game 2")
    )

    private val sampleLargeGames = listOf(
        dummyGame.copy(1000L, name = "Game 1"),
        dummyGame.copy(1001L, name = "Game 2"),
        dummyGame.copy(1002L, name = "Game 3"),
        dummyGame.copy(1003L, name = "Game 4"),
        dummyGame.copy(1004L, name = "Game 5"),
        dummyGame.copy(1005L, name = "Game 6"),
        dummyGame.copy(1006L, name = "Game 7"),
        dummyGame.copy(1007L, name = "Game 8"),
        dummyGame.copy(1008L, name = "Game 9"),
        dummyGame.copy(1009L, name = "Game 10"),
        dummyGame.copy(1010L, name = "Game 11"),
    )
}