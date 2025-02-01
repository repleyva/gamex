package com.repleyva.gamexapp.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.domain.constants.Constants.dummyGame
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
    fun `getBookmarkedGames fetches and updates state`() = runTest {
        val sampleGames = sampleGames
        coEvery { gameUseCase.getBookmarkedGames() } returns flowOf(sampleGames)

        viewModel.eventHandler(Init)

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