package com.repleyva.core.data.repository

import com.repleyva.core.domain.source.LocalDataSource
import com.repleyva.core.data.local.entity.GameEntity
import com.repleyva.core.data.remote.RemoteDataSource
import com.repleyva.core.data.remote.model.GameItem
import com.repleyva.core.data.remote.model.GameTrailerResponse
import com.repleyva.core.data.remote.model.GamesResponse
import com.repleyva.core.data.repository.base.NetworkBoundResource
import com.repleyva.core.domain.enums.Range
import com.repleyva.core.domain.model.Game
import com.repleyva.core.domain.repository.GamesRepository
import com.repleyva.core.domain.extensions.getDateRange
import com.repleyva.core.domain.vo.Resource
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : GamesRepository {

    override fun getAllGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, GamesResponse>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getAllGames().map { games ->
                    games.map { Game(it) }
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): ApiResponse<GamesResponse> =
                remoteDataSource.getAllGames()

            override suspend fun saveCallResult(data: GamesResponse) {
                localDataSource.insertGames(data.results?.map { GameEntity(it) }.orEmpty())
            }
        }.asFlow()

    override fun getHotGames(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, GamesResponse>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getHotGames().map { games ->
                    games.map { Game(it) }
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): ApiResponse<GamesResponse> =
                remoteDataSource.getAllGames(
                    dates = getDateRange(range = Range.MONTH, isPast = true),
                    ordering = "-rating",
                    page = 1,
                    pageSize = 10,
                )

            override suspend fun saveCallResult(data: GamesResponse) {
                localDataSource.insertGames(data.results?.map { GameEntity(it) }.orEmpty())
            }
        }.asFlow()

    override fun getGameDetails(id: Long): Flow<Resource<Game>> =
        object : NetworkBoundResource<Game, GameItem>() {
            override fun loadFromDB(): Flow<Game> {
                return localDataSource.getGameDetail(id).map { Game(it) }
            }

            override suspend fun createCall(): ApiResponse<GameItem> =
                remoteDataSource.getGameDetails(id)

            override suspend fun saveCallResult(data: GameItem) {
                localDataSource.updateGameDescription(data.id ?: 0, data.description.orEmpty())
            }

            override fun shouldFetch(data: Game?): Boolean =
                data?.description.isNullOrEmpty()

        }.asFlow()

    override fun getGameTrailer(id: Long): Flow<Resource<Game>> =
        object : NetworkBoundResource<Game, GameTrailerResponse>() {
            override fun loadFromDB(): Flow<Game> {
                return localDataSource.getGameDetail(id).map { Game(it) }
            }

            override suspend fun createCall(): ApiResponse<GameTrailerResponse> =
                remoteDataSource.getGameTrailers(id)

            override suspend fun saveCallResult(data: GameTrailerResponse) {
                localDataSource.updateGameTrailer(id, data.results?.firstOrNull()?.data?.x480.orEmpty())
            }

            override fun shouldFetch(data: Game?): Boolean =
                data?.trailerUrl == null

        }.asFlow()

    override fun searchGame(query: String): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, GamesResponse>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.searchGames(query).map { games ->
                    games.map { Game(it) }
                }
            }

            override suspend fun createCall(): ApiResponse<GamesResponse> =
                remoteDataSource.getAllGames(search = query)

            override suspend fun saveCallResult(data: GamesResponse) {
                localDataSource.insertGames(data.results?.map { GameEntity(it) }.orEmpty())
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data.isNullOrEmpty()

        }.asFlow()

    override suspend fun setIsFavorites(
        isFavorites: Boolean,
        id: Long,
    ) {
        localDataSource.setIsFavorites(isFavorites, id)
    }

    override fun getAllFavoritesGames(): Flow<List<Game>> {
        return localDataSource.getAllFavoriteGames().map { games ->
            games.map { Game(it) }
        }
    }
}
