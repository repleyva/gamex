package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.bookmark.BookmarkViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: BookmarkViewModel

    @RelaxedMockK
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = BookmarkViewModel(gameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should fetch bookmarked games when Init event is triggered`() = runTest {
        // Given
        val games = listOf(sampleGame())
        coEvery { gameUseCase.getBookmarkedGames() } returns flowOf(games)

        // When
        viewModel.eventHandler(Init)

        // Then
        viewModel.uiState.test {
            val state = expectMostRecentItem()
            assertThat(state.games).isEqualTo(games)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun sampleGame() = Game(
        id = 1L,
        slug = "sample-game",
        name = "Sample Game",
        released = "2023-01-01",
        tba = false,
        backgroundImage = "image_url",
        rating = 4.5,
        ratingTop = 5,
        ratingsCount = 100,
        reviewsTextCount = 50,
        added = 200,
        metacritic = 85,
        playtime = 10,
        suggestionsCount = 5,
        updated = "2023-01-02",
        reviewsCount = 30,
        saturatedColor = "#FFFFFF",
        dominantColor = "#000000",
        parentPlatforms = listOf("PC"),
        genres = listOf("Action"),
        stores = listOf("Steam"),
        tags = listOf("Singleplayer"),
        esrbRating = "Mature",
        shortScreenshots = listOf("screenshot_url"),
        isFavorites = true,
        description = "Sample game description",
        trailerUrl = "trailer_url"
    )
}

