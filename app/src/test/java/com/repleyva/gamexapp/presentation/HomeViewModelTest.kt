package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.home.HomeViewModel
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
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    @MockK(relaxed = true)
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(gameUseCase)
    }

    @Test
    fun `init should call getAllGames and getHotGames`() = runTest {
        coEvery { gameUseCase.getAllGames() } returns flowOf(Success(emptyList()))
        coEvery { gameUseCase.getHotGames() } returns flowOf(Success(emptyList()))

        viewModel.eventHandler(Init)

        coVerify { gameUseCase.getAllGames() }
        coVerify { gameUseCase.getHotGames() }
    }

    @Test
    fun `getAllGames should update state with success`() = runTest {
        val games = listOf(fakeGame)
        coEvery { gameUseCase.getAllGames() } returns flowOf(Success(games))

        viewModel.eventHandler(Init)

        viewModel.uiState.test {
            assertThat(expectMostRecentItem().games).isEqualTo(games)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllGames should update state with error when fails`() = runTest {
        val errorMessage = "Error fetching games"
        coEvery { gameUseCase.getAllGames() } returns flowOf(Error(errorMessage))

        viewModel.eventHandler(Init)

        viewModel.uiState.test {
            assertEquals(expectMostRecentItem().error, errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getHotGames should update state with success`() = runTest {
        val hotGames = listOf(fakeGame)
        coEvery { gameUseCase.getHotGames() } returns flowOf(Success(hotGames))

        viewModel.eventHandler(Init)

        viewModel.uiState.test {
            assertEquals(expectMostRecentItem().hotGames, hotGames)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getHotGames should update state with error when fails`() = runTest {
        val errorMessage = "Error fetching hot games"
        coEvery { gameUseCase.getHotGames() } returns flowOf(Error(errorMessage))

        viewModel.eventHandler(Init)

        viewModel.uiState.test {
            assertEquals(expectMostRecentItem().error, errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private val fakeGame =
        Game(
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

