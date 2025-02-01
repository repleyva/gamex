package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.*
import com.repleyva.gamexapp.presentation.screens.search.SearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SearchViewModel

    @MockK(relaxed = true)
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(gameUseCase)
    }

    @Test
    fun `onSearchQueryChanged should update query and trigger search`() = runTest {
        coEvery { gameUseCase.searchGames("test") } returns flowOf(Success(listOf(fakeGame)))

        viewModel.eventHandler(OnSearchQueryChange("test"))

        viewModel.uiState.test {
            assert(expectMostRecentItem().query == "test")
            delay(500L)
            assert(expectMostRecentItem().games.isNotEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSearchQueryChanged should clear games when query is empty`() = runTest {
        viewModel.eventHandler(OnSearchQueryChange(""))

        viewModel.uiState.test {
            assert(expectMostRecentItem().games.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchGames should update state with success`() = runTest {
        val games = listOf(fakeGame)
        coEvery { gameUseCase.searchGames("test") } returns flowOf(Success(games))

        viewModel.eventHandler(OnSearchQueryChange("test"))
        delay(500L)

        viewModel.uiState.test {
            assert(expectMostRecentItem().games == games)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchGames should update state with error when fails`() = runTest {
        val errorMessage = "Error fetching games"
        coEvery { gameUseCase.searchGames("test") } returns flowOf(Error(errorMessage))

        viewModel.eventHandler(OnSearchQueryChange("test"))
        delay(500L)

        viewModel.uiState.test {
            assert(expectMostRecentItem().error == errorMessage)
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
