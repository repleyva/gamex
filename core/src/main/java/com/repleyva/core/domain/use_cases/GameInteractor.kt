package com.repleyva.core.domain.use_cases

import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.repository.GamesRepository
import com.repleyva.core.domain.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(private val repository: GamesRepository) : GameUseCase {

    override fun getAllGames(): Flow<Resource<List<Game>>> {
        return repository.getAllGames()
    }

    override fun getHotGames(): Flow<Resource<List<Game>>> {
        return repository.getHotGames()
    }

    override suspend fun setIsFavorites(
        isFavorites: Boolean,
        id: Long,
    ) {
        repository.setIsFavorites(isFavorites, id)
    }

    override fun getBookmarkedGames(): Flow<List<Game>> {
        return repository.getAllFavoritesGames()
    }

    override fun getGameDetails(id: Long): Flow<Resource<Game>> {
        return repository.getGameDetails(id)
    }

    override fun fetchGameTrailer(id: Long): Flow<Resource<Game>> {
        return repository.getGameTrailer(id)
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> {
        return repository.searchGame(query)
    }

}