package com.repleyva.core.data.remote

import com.repleyva.core.data.remote.model.GameItem
import com.repleyva.core.data.remote.model.GameTrailerResponse
import com.repleyva.core.data.remote.model.GamesResponse
import com.repleyva.core.data.remote.api.ApiService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiService) : ApiService {

    override suspend fun getAllGames(
        page: Int?,
        pageSize: Int?,
        search: String?,
        searchPrecise: Boolean?,
        searchExact: Boolean?,
        parentPlatforms: String?,
        platforms: String?,
        stores: String?,
        developers: String?,
        publishers: String?,
        genres: String?,
        tags: String?,
        creators: String?,
        dates: String?,
        updated: String?,
        platformsCount: Int?,
        metacritic: String?,
        excludeCollection: Int?,
        excludeAdditions: Boolean?,
        excludeParents: Boolean?,
        excludeGameSeries: Boolean?,
        excludeStores: String?,
        ordering: String?,
    ): ApiResponse<GamesResponse> {
        return api.getAllGames(
            page = page,
            pageSize = pageSize,
            ordering = ordering,
            search = search,
            searchPrecise = searchPrecise,
            searchExact = searchExact,
            parentPlatforms = parentPlatforms,
            platforms = platforms,
            platformsCount = platformsCount,
            creators = creators,
            developers = developers,
            publishers = publishers,
            genres = genres,
            tags = tags,
            stores = stores,
            dates = dates,
            updated = updated,
            metacritic = metacritic,
            excludeStores = excludeStores,
            excludeCollection = excludeCollection,
            excludeAdditions = excludeAdditions,
            excludeParents = excludeParents,
            excludeGameSeries = excludeGameSeries
        )
    }

    override suspend fun getGameDetails(id: Long): ApiResponse<GameItem> {
        return api.getGameDetails(id)
    }

    override suspend fun getGameTrailers(id: Long): ApiResponse<GameTrailerResponse> {
        return api.getGameTrailers(id)
    }

}

