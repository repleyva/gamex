package com.repleyva.core.data.local

import com.repleyva.core.data.local.dao.GameDao
import com.repleyva.core.data.local.entity.GameEntity
import com.repleyva.core.domain.extensions.getLastMonthDate
import com.repleyva.core.domain.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gameDao: GameDao,
) : LocalDataSource {

    override fun getAllGames(): Flow<List<GameEntity>> {
        return gameDao.getAllGames()
    }

    override fun getHotGames(): Flow<List<GameEntity>> {
        return gameDao.getHotGames(getLastMonthDate())
    }

    override fun searchGames(query: String): Flow<List<GameEntity>> {
        return gameDao.searchGames(query)
    }

    override fun getGameDetail(id: Long): Flow<GameEntity?> {
        return gameDao.getGameDetails(id)
    }

    override suspend fun setIsFavorites(
        isFavorites: Boolean,
        id: Long,
    ) {
        gameDao.setIsFavorites(isFavorites, id)
    }

    override fun getAllFavoriteGames(): Flow<List<GameEntity>> {
        return gameDao.getAllFavoriteGames()
    }

    override suspend fun insertGames(games: List<GameEntity>) {
        gameDao.insertGames(games)
    }

    override suspend fun updateGameDescription(
        id: Long,
        description: String,
    ) {
        gameDao.updateGameDescription(id, description)
    }

    override suspend fun updateGameTrailer(
        id: Long,
        trailerUrl: String,
    ) {
        gameDao.updateGameTrailer(id, trailerUrl)
    }
}