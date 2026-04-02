package com.vroff.domain.repository

import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    suspend fun getNowPlayingMoviePreview(
        language: String?,
        region: String?,
    ): Result<List<MovieMediaItem>>

    fun getNowPlayingMovie(
        language: String?,
        region: String?,
    ): Flow<PagingData<MovieMediaItem>>

    suspend fun getUpcomingMoviePreview(
        language: String?,
        region: String?,
    ): Result<List<MovieMediaItem>>

    suspend fun getPopularMoviePreview(
        language: String?,
        region: String?,
    ): Result<List<MovieMediaItem>>
}
