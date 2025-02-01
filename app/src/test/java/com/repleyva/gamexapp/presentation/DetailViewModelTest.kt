package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.BookmarkGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.detail.DetailScreenEvent.ShareGame
import com.repleyva.gamexapp.presentation.screens.detail.DetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class DetailsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: DetailViewModel

    @RelaxedMockK
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(gameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When the init event is called, validate the game into state`() = runTest {
        val game = dummyGame.copy(1000L)
        coEvery { gameUseCase.getGameDetails(game.id) } returns flowOf(Success(game))

        viewModel.eventHandler(Init(game))

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.game).isEqualTo(game)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When the init event is called it validates that fetchGameTrailer has been called`() = runTest {
        val game = dummyGame.copy(1000L)
        coEvery { gameUseCase.fetchGameTrailer(game.id) } returns flowOf(Success(game))

        viewModel.eventHandler(Init(game))

        coVerify { gameUseCase.fetchGameTrailer(game.id) }
    }

    @Test
    fun `When the ShareGame with dismissed false event is called, it validates the state`() = runTest {
        val game = dummyGame.copy(1000L)

        viewModel.eventHandler(ShareGame(game))

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.shareSheetGame).isEqualTo(game)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When the ShareGame with dismissed true event is called, it validates the state`() = runTest {
        val game = dummyGame.copy(1000L)

        viewModel.eventHandler(ShareGame(game, true))

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.shareSheetGame).isEqualTo(null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When the BookmarkGame event is called, it validates that setIsFavorites has been called`() = runTest {
        val game = dummyGame.copy(1000L)

        viewModel.eventHandler(BookmarkGame(game.id, true))

        coVerify { gameUseCase.setIsFavorites(true, game.id) }
    }
}