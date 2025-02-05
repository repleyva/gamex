package com.repleyva.gamexapp.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.core.domain.enums.ConverterDate
import com.repleyva.core.domain.extensions.convertDateTo
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreen
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.BookmarkGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.ShareGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenState
import com.repleyva.gamexapp.presentation.ui.theme.GamexAppTheme
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun verify_ShareButton_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = { assert(it is ShareGame) }
                )
            }
        }

        composeTestRule.onNodeWithTag("shareButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("shareButton").performClick()
    }

    @Test
    fun verify_OnBackPress_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = { assert(true) },
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("backButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("backButton").performClick()
    }

    @Test
    fun verify_Image_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("backgroundImage").assertIsDisplayed()
    }

    @Test
    fun verify_TrailerButton_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = { assert(true) },
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("playTrailerButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("playTrailerButton").performClick()
    }

    @Test
    fun verify_Title_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }

        }

        composeTestRule.onNodeWithText(sampleGame.name).assertIsDisplayed()
    }

    @Test
    fun verify_SaveButton_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = { assert(it is BookmarkGame && it.bookmarked) }
                )
            }
        }

        composeTestRule.onNodeWithTag("saveButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("saveButton").performClick()
    }

    @Test
    fun verify_GeneralInfo_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("generalInfo").assertIsDisplayed()
    }

    @Test
    fun verify_MetaScore_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_metascore)).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleGame.metacritic.toString()).assertIsDisplayed()
    }

    @Test
    fun verify_Rating_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_rating)).assertIsDisplayed()
        composeTestRule.onNodeWithTag("ratingBar").assertIsDisplayed()
    }

    @Test
    fun verify_Released_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_released)).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleGame.released.convertDateTo(ConverterDate.FULL_DATE)).assertIsDisplayed()
    }

    @Test
    fun verify_Genre_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }


        composeTestRule.onNodeWithText(context.getString(R.string.copy_genre)).assertIsDisplayed()
        composeTestRule.onNodeWithTag("genres").assertIsDisplayed()
    }

    @Test
    fun verify_Description_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_description)).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleGame.description.ifBlank { "-" }).assertIsDisplayed()
    }

    @Test
    fun verify_Screenshots_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_screenshots)).assertIsDisplayed()
        composeTestRule.onNodeWithTag("screenshots").assertIsDisplayed()
    }

    @Test
    fun verify_Tags_inDetailScreen() {
        composeTestRule.setContent {
            GamexAppTheme {
                DetailScreen(
                    state = DetailScreenState(game = sampleGame),
                    game = sampleGame,
                    onPlayTrailer = {},
                    onNavigateBack = {},
                    eventHandler = {}
                )
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.copy_tags)).assertIsDisplayed()
        composeTestRule.onNodeWithTag("tags").assertIsDisplayed()
    }

    private val sampleGame = dummyGame.copy(
        id = 1000L,
        name = "Game 1",
        backgroundImage = "backgroundImage",
        trailerUrl = "trailerUrl",
        tags = listOf("Tag 1", "Tag 2"),
        genres = listOf("Genre 1", "Genre 2"),
        shortScreenshots = listOf("screenshot1", "screenshot2"),
    )
}