package com.repleyva.core.data

import com.repleyva.core.data.remote.RemoteDataSource
import com.repleyva.core.data.remote.api.ApiService
import com.repleyva.core.data.remote.model.GameItem
import com.repleyva.core.data.remote.model.GameTrailerResponse
import com.repleyva.core.data.remote.model.GamesResponse
import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private val apiService: ApiService = mockk()

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `getAllGames should return ApiResponse with GamesResponse`() = runTest {
        val mockResponse: ApiResponse<GamesResponse> = ApiResponse.Success(GamesResponse(results = dummyGames))
        coEvery { apiService.getAllGames(page = 1, pageSize = 20, search = "test") } returns mockResponse

        val result = remoteDataSource.getAllGames(page = 1, pageSize = 20, search = "test")

        assertEquals(mockResponse, result)
    }

    @Test
    fun `getGameDetails should return ApiResponse with GameItem`() = runTest {
        val mockResponse: ApiResponse<GameItem> = ApiResponse.Success(dummyGames.first())
        coEvery { apiService.getGameDetails(any()) } returns mockResponse

        val result = remoteDataSource.getGameDetails(id = 1L)

        assertEquals(mockResponse, result)
    }

    @Test
    fun `getGameTrailers should return ApiResponse with GameTrailerResponse`() = runTest {
        val mockResponse: ApiResponse<GameTrailerResponse> = ApiResponse.Success(GameTrailerResponse())
        coEvery { apiService.getGameTrailers(any()) } returns mockResponse

        val result = remoteDataSource.getGameTrailers(id = 1L)

        assertEquals(mockResponse, result)
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
    )
}
