package com.vroff.domain.repository

import com.vroff.domain.model.tmdb.movie.MovieAppendedToResponse
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.movie.SeriesAppendedToResponse
import com.vroff.domain.model.tmdb.series.SeriesDetail

interface CacheRepository {
    suspend fun getMovie(
        movieId: Int,
        language: String,
        appendToResponse: List<MovieAppendedToResponse>,
    ): Result<MovieDetail>

    suspend fun saveMovieToCache(
        domain: MovieDetail,
        language: String,
        appendToResponse: List<MovieAppendedToResponse>,
    )

    suspend fun saveSeriesToCache(
        domain: SeriesDetail,
        language: String,
        appendToResponse: List<SeriesAppendedToResponse>,
    )

    suspend fun getSeries(
        seriesId: Int,
        language: String,
        appendToResponse: List<SeriesAppendedToResponse>,
    ): Result<SeriesDetail>
}
