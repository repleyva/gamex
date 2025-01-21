package com.repleyva.core.domain.use_cases

import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.repository.GamesRepository
import com.repleyva.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(private val repo: GamesRepository) : GameUseCase {

    override fun getAllGames(): Flow<Resource<List<Game>>> {
        return repo.getAllGames()
    }

    override fun getHotGames(): Flow<Resource<List<Game>>> {
        return repo.getHotGames()
    }

    override suspend fun setIsFavorites(
        isFavorites: Boolean,
        id: Long,
    ) {
        repo.setIsFavorites(isFavorites, id)
    }

    override fun getBookmarkedGames(): Flow<List<Game>> {
        return repo.getAllFavoritesGames()
    }

    override fun getGameDetails(id: Long): Flow<Resource<Game>> {
        return repo.getGameDetails(id)
    }

    override fun fetchGameTrailer(id: Long): Flow<Resource<Game>> {
        return repo.getGameTrailer(id)
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> {
        return repo.searchGame(query)
    }

}