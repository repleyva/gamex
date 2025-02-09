package com.repleyva.gamexapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.core.data.local.dao.GameDao
import com.repleyva.core.data.local.entity.GameEntity
import com.repleyva.core.data.local.room.GameDatabase
import com.repleyva.core.data.remote.model.GameItem
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

@RunWith(AndroidJUnit4::class)
@SmallTest
class GameDaoTest {

    private lateinit var gameDao: GameDao
    private lateinit var db: GameDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        gameDao = db.gameDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::db.isInitialized) {
            db.close()
        }
    }

    @Test
    fun gameDao_insertGames_insertsGamesSuccessfully() = runTest {
        // Given
        val games = dummyGames

        // When
        gameDao.insertGames(games)

        // Then
        gameDao.getAllGames().test {
            val result = awaitItem()
            assertThat(result).containsExactlyElementsIn(games)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun gameDao_getHotGames_returnsTopRatedGames() = runTest {
        // Given
        val game1 = dummyGames.first()
        val game2 = dummyGames.last()

        gameDao.insertGames(listOf(game1, game2))

        // When & Then
        gameDao.getHotGames(Date()).test {
            val result = awaitItem()
            assertThat(result).containsExactly(game2, game1)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun gameDao_updateGameDescription_updatesDescription() = runTest {
        // Given
        val game = dummyGames.first()
        gameDao.insertGames(listOf(game))
        val newDescription = "Updated description"

        // When
        gameDao.updateGameDescription(game.id, newDescription)

        // Then
        gameDao.getGameDetails(game.id).test {
            val result = awaitItem()
            assertThat(result?.description).isEqualTo(newDescription)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun gameDao_searchGames_returnsMatchingGames() = runTest {
        // Given
        val game1 = dummyGames.first()
        val game2 = dummyGames.last()
        gameDao.insertGames(listOf(game1, game2))

        // When & Then
        gameDao.searchGames("Game 1").test {
            val result = awaitItem()
            assertThat(result).containsExactly(game1)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun gameDao_setIsFavorites_marksGameAsFavorite() = runTest {
        // Given
        val game = dummyGames.first()
        gameDao.insertGames(listOf(game))

        // When
        gameDao.setIsFavorites(true, game.id)

        // Then
        gameDao.getAllFavoriteGames().test {
            val result = awaitItem()
            assertThat(result).containsExactly(game.copy(isFavorites = true))
            cancelAndConsumeRemainingEvents()
        }
    }

    private val dummyGames = listOf(
        GameItem(
            id = 1L,
            name = "Game 1",
            description = "Description 1",
            released = "2025-02-09",
            rating = 4.5,
        ),
        GameItem(
            id = 2L,
            name = "Game 2",
            description = "Description 2",
            released = "2025-03-01",
            rating = 5.0,
        ),
    ).map { GameEntity(it) }
}
