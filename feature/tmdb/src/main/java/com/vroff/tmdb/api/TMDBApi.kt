package com.vroff.tmdb.api

import com.vroff.domain.model.NetworkResult
import com.vroff.network.paging.PagerResponse
import com.vroff.tmdb.Endpoint
import com.vroff.tmdb.entity.Configuration
import com.vroff.tmdb.entity.movie.MovieDetailDTO
import com.vroff.tmdb.entity.profile.ProfileDetailDTO
import com.vroff.tmdb.entity.search.GenresDTO
import com.vroff.tmdb.entity.search.MediaItemDTO
import com.vroff.tmdb.entity.series.SeriesDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET(Endpoint.CONFIGURATION)
    suspend fun getConfiguration(): NetworkResult<Configuration>

    @GET(Endpoint.SERIES_GENRES)
    suspend fun getSeriesGenres(): NetworkResult<GenresDTO>

    @GET(Endpoint.MOVIE_GENRES)
    suspend fun getMovieGenres(): NetworkResult<GenresDTO>

    @GET(Endpoint.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String?,
    ): NetworkResult<MovieDetailDTO>

    @GET(Endpoint.SERIES_DETAILS)
    suspend fun getSerialDetails(
        @Path("series_id") seriesId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String?,
    ): NetworkResult<SeriesDetailDTO>

    @GET(Endpoint.MULTI_SEARCH)
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("region") region: String,
    ): NetworkResult<PagerResponse<MediaItemDTO>>

    @GET(Endpoint.PROFILE_DETAILS)
    suspend fun getProfileDetail(
        @Path("profile_id") profileId: Int,
        @Query("language") language: String,
        @Query("append_to_response") appendToResponse: String?,
    ): NetworkResult<ProfileDetailDTO>
}
