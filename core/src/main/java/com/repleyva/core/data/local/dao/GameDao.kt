package com.repleyva.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repleyva.core.data.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE released >= :date ORDER BY rating DESC LIMIT 20 ")
    fun getHotGames(date: Date): Flow<List<GameEntity>>

    @Query("UPDATE game SET description = :description WHERE id = :id")
    fun updateGameDescription(
        id: Long,
        description: String,
    )

    @Query("UPDATE game SET trailer_url = :trailerUrl WHERE id = :id")
    fun updateGameTrailer(
        id: Long,
        trailerUrl: String,
    )

    @Query("SELECT * FROM game WHERE name LIKE '%' || :query || '%'")
    fun searchGames(query: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGameDetails(id: Long): Flow<GameEntity?>

    @Query("UPDATE game SET is_favorites = :isFavorites WHERE id = :id")
    suspend fun setIsFavorites(
        isFavorites: Boolean,
        id: Long,
    )

    @Query("SELECT * FROM game WHERE is_favorites = 1")
    fun getAllFavoriteGames(): Flow<List<GameEntity>>
}