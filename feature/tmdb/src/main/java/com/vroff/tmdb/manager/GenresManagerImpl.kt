package com.vroff.tmdb.manager

import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.repository.GenresManager
import com.vroff.domain.storage.DoubleTapeDataStore
import com.vroff.domain.util.safeApiCall
import com.vroff.tmdb.api.TMDBApi
import com.vroff.tmdb.entity.search.GenresDTO
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GenresManagerImpl
    @Inject
    constructor(
        private val api: TMDBApi,
        private val storage: DoubleTapeDataStore,
    ) : GenresManager {
        override var movieGenres: List<Genre>? = null
        override var seriesGenres: List<Genre>? = null

        override suspend fun loadMovieGenres() {
            movieGenres = loadGenre(movieGenres, api::getMovieGenres, DoubleTapeDataStore.Keys.Genres.Movie)
        }

        override suspend fun loadSeriesGenres() {
            seriesGenres = loadGenre(seriesGenres, api::getSeriesGenres, DoubleTapeDataStore.Keys.Genres.Series)
        }

        override suspend fun loadGenres() {
            loadMovieGenres()
            loadSeriesGenres()
        }

        suspend fun loadGenre(
            cached: List<Genre>?,
            apiCall: suspend () -> NetworkResult<GenresDTO>,
            key: DoubleTapeDataStore.Keys,
        ): List<Genre>? {
            if (cached != null) return cached
            var updated =
                storage
                    .get<List<Genre>>(key)
                    .first()
            if (updated != null) return updated
            updated =
                apiCall
                    .invoke()
                    .safeApiCall { genres ->
                        genres.genres.map {
                            Genre(it.id, it.name)
                        }
                    }.onSuccess {
                        storage.save(key, it)
                    }.getOrNull()

            return updated
        }
    }
