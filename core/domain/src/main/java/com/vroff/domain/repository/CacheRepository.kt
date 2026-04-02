package com.vroff.domain.repository

import com.vroff.domain.model.tmdb.movie.MovieDetail

interface CacheRepository {
    suspend fun getMovie(
        movieId: Int,
        language: String,
    ): Result<MovieDetail>

    suspend fun saveMovieToCache(
        domain: MovieDetail,
        language: String,
    )
}
