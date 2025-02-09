package com.repleyva.core.domain

import com.repleyva.core.data.remote.model.GameItem
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.repository.GamesRepository
import com.repleyva.core.domain.use_cases.GameInteractor
import com.repleyva.core.domain.use_cases.GameUseCase
import com.repleyva.core.domain.vo.Resource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameInteractorTest {

    private lateinit var gameUseCase: GameUseCase

    @MockK
    private lateinit var gamesRepository: GamesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gameUseCase = GameInteractor(gamesRepository)
    }

    @Test
    fun `getAllGames should return a list of games`() = runTest {
        val games = dummyGames
        coEvery { gamesRepository.getAllGames() } returns flowOf(Resource.Success(games))

        val result = gameUseCase.getAllGames().first()

        assert(result is Resource.Success)
        assertEquals(3, (result as Resource.Success).data.size)
    }

    @Test
    fun `getHotGames should return a list of hot games`() = runTest {
        val hotGames = dummyGames.take(1)
        coEvery { gamesRepository.getHotGames() } returns flowOf(Resource.Success(hotGames))

        val result = gameUseCase.getHotGames().first()

        assert(result is Resource.Success)
        assertEquals(1, (result as Resource.Success).data.size)
    }

    @Test
    fun `setIsFavorites should update favorite status`() = runTest {
        coEvery { gamesRepository.setIsFavorites(true, 1) } just Runs

        gameUseCase.setIsFavorites(true, 1)

        coVerify { gamesRepository.setIsFavorites(true, 1) }
    }

    @Test
    fun `getBookmarkedGames should return a list of favorite games`() = runTest {
        val favoriteGames = dummyGames.take(1).map { it.copy(isFavorites = true, name = "Favorite Game") }
        coEvery { gamesRepository.getAllFavoritesGames() } returns flowOf(favoriteGames)

        val result = gameUseCase.getBookmarkedGames().first()

        assertEquals(1, result.size)
        assertEquals("Favorite Game", result[0].name)
    }

    @Test
    fun `getGameDetails should return game details`() = runTest {
        val game = dummyGames.take(1).first().copy(description = "Details")
        coEvery { gamesRepository.getGameDetails(1) } returns flowOf(Resource.Success(game))

        val result = gameUseCase.getGameDetails(1).first()

        assert(result is Resource.Success)
        assertEquals("Details", (result as Resource.Success).data.description)
    }

    @Test
    fun `fetchGameTrailer should return game trailer`() = runTest {
        val gameWithTrailer = dummyGames.first().copy(trailerUrl = "https://trailer.com")
        coEvery { gamesRepository.getGameTrailer(1) } returns flowOf(Resource.Success(gameWithTrailer))

        val result = gameUseCase.fetchGameTrailer(1).first()

        assert(result is Resource.Success)
        assertEquals("https://trailer.com", (result as Resource.Success).data.trailerUrl)
    }

    @Test
    fun `searchGames should return a list of games`() = runTest {
        val searchResults = dummyGames.take(1)
        coEvery { gamesRepository.searchGame("Game") } returns flowOf(Resource.Success(searchResults))

        val result = gameUseCase.searchGames("Game").first()

        assert(result is Resource.Success)
        assertEquals(1, (result as Resource.Success).data.size)
    }

    private val dummyGames = listOf(
        GameItem(
            id = 1,
            slug = "slug",
            description = "description",
            name = "name",
        ),
        GameItem(
            id = 2,
            slug = "slug",
            description = "description",
            name = "name",
        ),
        GameItem(
            id = 3,
            slug = "slug",
            description = "description",
            name = "name",
        )
    ).map { Game(it) }
}
