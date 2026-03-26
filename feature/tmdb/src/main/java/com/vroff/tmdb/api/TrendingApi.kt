package com.vroff.tmdb.api

import com.vroff.domain.model.NetworkResult
import com.vroff.network.paging.PagerResponse
import com.vroff.network.paging.TimedPagerResponse
import com.vroff.tmdb.Endpoint
import com.vroff.tmdb.entity.search.MediaItemDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApi {
    @GET(Endpoint.UPCOMING_MOVIE)
    suspend fun getUpcomingMovie(
        @Query("page") page: Int,
        @Query("language") language: String?,
        @Query("region") region: String?,
    ): NetworkResult<TimedPagerResponse<MediaItemDTO>>

    @GET(Endpoint.NOW_PLAYING_MOVIE)
    suspend fun getNowPlayingMovie(
        @Query("page") page: Int,
        @Query("language") language: String?,
        @Query("region") region: String?,
    ): NetworkResult<TimedPagerResponse<MediaItemDTO>>

    @GET(Endpoint.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("page") page: Int,
        @Query("language") language: String?,
        @Query("region") region: String?,
    ): NetworkResult<PagerResponse<MediaItemDTO>>
}
