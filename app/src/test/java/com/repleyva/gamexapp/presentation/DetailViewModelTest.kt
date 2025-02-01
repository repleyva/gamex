package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.BookmarkGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.ShareGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: DetailViewModel

    @MockK(relaxed = true)
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(gameUseCase)
    }

    @Test
    fun `init should update state with game and fetch details and trailer`() = runTest {
        coEvery { gameUseCase.getGameDetails(fakeGame.id) } returns flowOf(Success(fakeGame))
        coEvery { gameUseCase.fetchGameTrailer(fakeGame.id) } returns flowOf(Success(fakeGame))

        viewModel.eventHandler(Init(fakeGame))

        viewModel.uiState.test {
            assertThat(expectMostRecentItem().game).isEqualTo(fakeGame)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { gameUseCase.getGameDetails(fakeGame.id) }
        coVerify { gameUseCase.fetchGameTrailer(fakeGame.id) }
    }

    @Test
    fun `fetchDetailGame should update state with success`() = runTest {
        coEvery { gameUseCase.getGameDetails(fakeGame.id) } returns flowOf(Success(fakeGame))

        viewModel.eventHandler(Init(fakeGame))

        viewModel.uiState.test {
            assertThat(expectMostRecentItem().game).isEqualTo(fakeGame)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchDetailGame should update state with error when fails`() = runTest {
        val errorMessage = "Error fetching game details"
        coEvery { gameUseCase.getGameDetails(fakeGame.id) } returns flowOf(Error(errorMessage))

        viewModel.eventHandler(Init(fakeGame))

        viewModel.uiState.test {
            assertThat(expectMostRecentItem().error).isEqualTo(errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchGameTrailer should update state with success`() = runTest {
        coEvery { gameUseCase.fetchGameTrailer(fakeGame.id) } returns flowOf(Success(fakeGame))

        viewModel.eventHandler(Init(fakeGame))

        coVerify { gameUseCase.fetchGameTrailer(fakeGame.id) }
    }

    @Test
    fun `fetchGameTrailer should update state with error when fails`() = runTest {
        val errorMessage = "Error fetching game trailer"
        coEvery { gameUseCase.fetchGameTrailer(fakeGame.id) } returns flowOf(Error(errorMessage))

        viewModel.eventHandler(Init(fakeGame))

        viewModel.uiState.test {
            assertEquals(expectMostRecentItem().error, errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setBookmarked should call use case`() = runTest {
        viewModel.eventHandler(BookmarkGame(fakeGame.id, true))

        coVerify { gameUseCase.setIsFavorites(true, fakeGame.id) }
    }

    @Test
    fun `shareGame should update shareSheetGame state`() = runTest {
        viewModel.eventHandler(ShareGame(fakeGame, false))

        viewModel.uiState.test {
            assertThat(expectMostRecentItem().shareSheetGame).isEqualTo(fakeGame)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private val fakeGame = Game(
        id = 1,
        slug = "game-1",
        name = "Game 1",
        released = "2023-01-01",
        tba = false,
        backgroundImage = "url",
        rating = 4.5,
        ratingTop = 5,
        ratingsCount = 100,
        reviewsTextCount = 10,
        added = 500,
        metacritic = 85,
        playtime = 20,
        suggestionsCount = 15,
        updated = "2023-01-02",
        reviewsCount = 50,
        saturatedColor = "#FFFFFF",
        dominantColor = "#000000",
        parentPlatforms = listOf("PC", "PS5"),
        genres = listOf("Action", "RPG"),
        stores = listOf("Steam", "Epic"),
        tags = listOf("Multiplayer", "Adventure"),
        esrbRating = "Mature",
        shortScreenshots = listOf("url1", "url2"),
        isFavorites = false,
        description = "An awesome game",
        trailerUrl = "trailer_url"
    )
}
