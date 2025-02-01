package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.screens.search.SearchScreenEvent.OnSearchQueryChange
import com.repleyva.gamexapp.presentation.screens.search.SearchViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SearchViewModel

    @RelaxedMockK
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(gameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Validates that when OnSearchQueryChange is called the state is updated with query`() = runTest {
        val query = "query"
        viewModel.eventHandler(OnSearchQueryChange(query))

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.query).isEqualTo(query)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validates that when query is empty games is an empty list`() = runTest {
        val query = ""
        viewModel.eventHandler(OnSearchQueryChange(query))

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.games).isEqualTo(emptyList<Game>())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validates that when query is valid games have data`() = runTest {
        val query = "query"
        val sampleGames = sampleGames
        coEvery { gameUseCase.searchGames(query) } returns flowOf(Success(sampleGames))

        viewModel.eventHandler(OnSearchQueryChange(query))
        delay(600)

        coVerify { gameUseCase.searchGames(query) }
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.games).isEqualTo(sampleGames)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private val sampleGames = listOf(
        dummyGame.copy(1000L),
        dummyGame.copy(1001L)
    )
}