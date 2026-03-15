package com.vroff.domain.repository

import com.vroff.domain.model.tmdb.common.Genre

interface GenresManager {
    suspend fun loadGenres()

    suspend fun loadSeriesGenres()

    suspend fun loadMovieGenres()

    val movieGenres: List<Genre>?
    val seriesGenres: List<Genre>?
}
