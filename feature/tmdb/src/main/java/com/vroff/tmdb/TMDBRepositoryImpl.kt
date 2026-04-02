package com.vroff.tmdb

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.common.buildAppendQuery
import com.vroff.domain.model.tmdb.movie.MovieAppendedToResponse
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.model.tmdb.search.TMDBMediaItem
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.domain.repository.CacheRepository
import com.vroff.domain.repository.TMDBRepository
import com.vroff.domain.util.onSuccessCatching
import com.vroff.domain.util.safeApiCall
import com.vroff.network.paging.BasePagingSource
import com.vroff.tmdb.api.TMDBApi
import com.vroff.tmdb.entity.common.GenreMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TMDBRepositoryImpl
    @Inject
    constructor(
        private val api: TMDBApi,
        private val genreMapper: GenreMapper,
        private val cacheRepository: CacheRepository,
    ) : TMDBRepository {
        override suspend fun getMovieDetails(
            movieId: Int,
            language: String,
            appendToResponse: List<MovieAppendedToResponse>,
        ): Result<MovieDetail> {
            val cachedMovie = cacheRepository.getMovie(movieId, language).getOrNull()
            if (cachedMovie != null && isMovieComplete(cachedMovie, appendToResponse)) {
                return Result.success(cachedMovie)
            }

            val api =
                api
                    .getMovieDetails(movieId, language, buildAppendQuery(appendToResponse))
                    .onSuccessCatching { cacheRepository.saveMovieToCache(it.toDomain(), language) }
                    .safeApiCall { it.toDomain() }
            return api
        }

        override suspend fun getSeriesDetails(
            seriesId: Int,
            language: String,
            appendToResponse: String?,
        ): Result<SeriesDetail> =
            api
                .getSerialDetails(seriesId, language, appendToResponse)
                .safeApiCall { it.mapToDomain() }

        override fun multiSearch(
            query: String,
            includeAdult: Boolean,
            language: String,
            region: String,
        ): Flow<PagingData<TMDBMediaItem>> =
            Pager(
                config = PagingConfig(20),
                pagingSourceFactory = {
                    BasePagingSource(
                        request = { api.multiSearch(query, it, includeAdult, language, region) },
                        mapper = {
                            it.mapToDomain(genreMapper::map)
                        },
                    )
                },
            ).flow

        override suspend fun getProfile(
            profileId: Int,
            language: String,
            appendToResponse: String?,
        ): Result<ProfileDetail> =
            api
                .getProfileDetail(
                    profileId,
                    language,
                    appendToResponse,
                ).safeApiCall {
                    it.mapToDomain()
                }

        private fun isMovieComplete(
            movie: MovieDetail,
            required: List<MovieAppendedToResponse>,
        ): Boolean =
            required.all { type ->
                when (type) {
                    MovieAppendedToResponse.CREDITS -> movie.credits != null
                    MovieAppendedToResponse.VIDEOS -> movie.videos != null
                }
            }
    }
