package com.vroff.domain.repository

import androidx.paging.PagingData
import com.vroff.domain.model.streaming_available.NetworkResult
import com.vroff.domain.model.tmdb.TMDBConfiguration
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.search.SearchResult
import com.vroff.domain.model.tmdb.series.SeriesDetail
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {

    suspend fun getMovieDetails(
        movieId: String,
        language: String,
        appendToResponse: String
    ): NetworkResult<MovieDetail>

    suspend fun getSeriesDetails(
        seriesId: String,
        language: String,
        appendToResponse: String
    ): NetworkResult<SeriesDetail>

    suspend fun multiSearch(
        query: String,
        page: Int,
        includeAdult: Boolean,
        language: String,
        region: String
    ): Flow<PagingData<SearchResult>>
}