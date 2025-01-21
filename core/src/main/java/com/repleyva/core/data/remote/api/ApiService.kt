package com.repleyva.core.data.remote.api

import com.repleyva.core.data.remote.model.GameItem
import com.repleyva.core.data.remote.model.GameTrailerResponse
import com.repleyva.core.data.remote.model.GamesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getAllGames(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("search") search: String? = null,
        @Query("search_precise") searchPrecise: Boolean? = null,
        @Query("search_exact") searchExact: Boolean? = null,
        @Query("parent_platforms") parentPlatforms: String? = null,
        @Query("platforms") platforms: String? = null,
        @Query("stores") stores: String? = null,
        @Query("developers") developers: String? = null,
        @Query("publishers") publishers: String? = null,
        @Query("genres") genres: String? = null,
        @Query("tags") tags: String? = null,
        @Query("creators") creators: String? = null,
        @Query("dates") dates: String? = null,
        @Query("updated") updated: String? = null,
        @Query("platforms_count") platformsCount: Int? = null,
        @Query("metacritic") metacritic: String? = null,
        @Query("exclude_collection") excludeCollection: Int? = null,
        @Query("exclude_additions") excludeAdditions: Boolean? = null,
        @Query("exclude_parents") excludeParents: Boolean? = null,
        @Query("exclude_game_series") excludeGameSeries: Boolean? = null,
        @Query("exclude_stores") excludeStores: String? = null,
        @Query("ordering") ordering: String? = null,
    ): ApiResponse<GamesResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Long,
    ): ApiResponse<GameItem>

    @GET("games/{id}/movies")
    suspend fun getGameTrailers(
        @Path("id") id: Long,
    ): ApiResponse<GameTrailerResponse>

}