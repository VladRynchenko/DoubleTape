package com.vroff.domain.repository

import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    fun getNowPlayingMoviePreview(
        language: String?,
        region: String?,
    ): Flow<Result<List<MovieMediaItem>>>

    fun getNowPlayingMovie(
        language: String?,
        region: String?,
    ): Flow<PagingData<MovieMediaItem>>

    fun getUpcomingMoviePreview(
        language: String?,
        region: String?,
    ): Flow<Result<List<MovieMediaItem>>>

    fun getPopularMoviePreview(
        language: String?,
        region: String?,
    ): Flow<Result<List<MovieMediaItem>>>
}
