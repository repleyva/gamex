package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.constants.Constants.dummyGame
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource.Error
import com.repleyva.core.domain.vo.Resource.Success
import com.repleyva.gamexapp.presentation.screens.home.HomeScreenEvent.Init
import com.repleyva.gamexapp.presentation.screens.home.HomeViewModel
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
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var gameUseCase: GameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(gameUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When the init event is called, validate the games into state`() = runTest {
        coEvery { gameUseCase.getAllGames() } returns flowOf(Success(dummyGames))

        viewModel.eventHandler(Init)

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.games).isEqualTo(dummyGames)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When the init event is called it validates that getHotGames has been called`() = runTest {
        coEvery { gameUseCase.getHotGames() } returns flowOf(Success(dummyGames))

        viewModel.eventHandler(Init)

        coVerify { gameUseCase.getHotGames() }
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.hotGames).isEqualTo(dummyGames)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validates that when there is an error in getAllGames the error is added to the state`() = runTest {
        val error = "Error Service"
        coEvery { gameUseCase.getAllGames() } returns flowOf(Error(error))

        viewModel.eventHandler(Init)

        coVerify { gameUseCase.getHotGames() }
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.error).isEqualTo(error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Validates that when there is an error in getHotGames the error is added to the state`() = runTest {
        val error = "Error Service"
        coEvery { gameUseCase.getHotGames() } returns flowOf(Error(error))

        viewModel.eventHandler(Init)

        coVerify { gameUseCase.getAllGames() }
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.error).isEqualTo(error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private val dummyGames = listOf(
        dummyGame.copy(1000L),
        dummyGame.copy(1001L)
    )
}