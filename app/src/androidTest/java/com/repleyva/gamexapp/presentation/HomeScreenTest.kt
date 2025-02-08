package com.repleyva.gamexapp.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.platform.app.InstrumentationRegistry
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.home.HomeScreen
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenState
import com.repleyva.gamexapp.presentation.ui.theme.GamexAppTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun verifyHomeScreen_TitleAndDescriptionDisplayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState()
                ) {}
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.copy_app_description)).assertIsDisplayed()
    }

    @Test
    fun verifyHomeScreen_IconDisplayed() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState()
                ) {}
            }
        }

        composeTestRule.onNodeWithTag("icon").assertIsDisplayed()
    }

    @Test
    fun verifyHomeScreen_HotGamesTitle() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState()
                ) {}
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_hot_games)).assertIsDisplayed()
    }

    @Test
    fun verifyHomeScreen_PopularGamesTitle() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState()
                ) {}
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copt_popular_games)).assertIsDisplayed()
    }

    @Test
    fun verifyHomeScreen_HotGamesItems() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState(hotGames = sampleLargeGames)
                ) {
                    assert(it == sampleLargeGames[0])
                }
            }
        }

        composeTestRule.onNodeWithText(sampleLargeGames[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleLargeGames[0].name).performClick()
    }

    @Test
    fun verifyHomeScreen_PopularGamesItems() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState(games = sampleLargeGames),
                    onDetailScreen = { assert(it == sampleLargeGames[0]) }
                )
            }
        }

        composeTestRule.onNodeWithText(sampleLargeGames[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleLargeGames[0].name).performClick()
    }

    @Test
    fun verifyHomeScreen_whenHotGameListIsLarge() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState(hotGames = sampleLargeGames)
                ) {
                    assert(it == sampleLargeGames[sampleLargeGames.lastIndex])
                }
            }
        }

        composeTestRule.onNodeWithTag("hotGamesTab").performScrollToIndex(sampleLargeGames.lastIndex)
        composeTestRule.onNodeWithText(sampleLargeGames[sampleLargeGames.lastIndex].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleLargeGames[sampleLargeGames.lastIndex].name).performClick()
    }

    @Test
    fun verifyHomeScreen_whenPopularGameListIsLarge() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState(games = sampleLargeGames)
                ) {
                    assert(it == sampleLargeGames[sampleLargeGames.lastIndex])
                }
            }
        }

        composeTestRule.onNodeWithTag("gamesTag").performScrollToIndex(sampleLargeGames.lastIndex)
        composeTestRule.onNodeWithText(sampleLargeGames[sampleLargeGames.lastIndex].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleLargeGames[sampleLargeGames.lastIndex].name).performClick()
    }

    @Test
    fun verifyHomeScreen_whenHotGameListIsLoading() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState(isLoadingHotGames = true)
                ) {}
            }
        }

        composeTestRule.onAllNodesWithTag("itemHorizontalShimmerTag").assertCountEquals(3)
    }

    @Test
    fun verifyHomeScreen_whenPopularGameListIsLoading() {
        composeTestRule.setContent {
            GamexAppTheme {
                HomeScreen(
                    state = HomeScreenState(isLoading = true)
                ) {}
            }
        }

        composeTestRule.onAllNodesWithTag("gameShimmerTag").assertCountEquals(5)
    }

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