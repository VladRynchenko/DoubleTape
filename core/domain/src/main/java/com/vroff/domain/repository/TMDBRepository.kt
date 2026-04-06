package com.vroff.domain.repository

import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.movie.MovieAppendedToResponse
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.movie.SeriesAppendedToResponse
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.model.tmdb.search.TMDBMediaItem
import com.vroff.domain.model.tmdb.series.SeriesDetail
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        appendToResponse: List<MovieAppendedToResponse> = emptyList(),
    ): Result<MovieDetail>

    suspend fun getSeriesDetails(
        seriesId: Int,
        language: String,
        appendToResponse: List<SeriesAppendedToResponse> = emptyList(),
    ): Result<SeriesDetail>

    fun multiSearch(
        query: String,
        includeAdult: Boolean,
        language: String,
        region: String,
    ): Flow<PagingData<TMDBMediaItem>>

    suspend fun getProfile(
        profileId: Int,
        language: String,
        appendToResponse: String? = null,
    ): Result<ProfileDetail>
}
