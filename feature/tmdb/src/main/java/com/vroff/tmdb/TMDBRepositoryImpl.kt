package com.vroff.tmdb

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.common.buildAppendQuery
import com.vroff.domain.model.tmdb.movie.MovieAppendedToResponse
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.movie.SeriesAppendedToResponse
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.model.tmdb.search.TMDBMediaItem
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.domain.repository.CacheRepository
import com.vroff.domain.repository.TMDBRepository
import com.vroff.domain.util.coRunCatching
import com.vroff.domain.util.onSuccessCatching
import com.vroff.domain.util.toResult
import com.vroff.network.paging.BasePagingSource
import com.vroff.network.retry
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
            coRunCatching {
                val cachedMovie = cacheRepository.getMovie(movieId, language, appendToResponse).getOrNull()
                if (cachedMovie != null && isMovieComplete(cachedMovie, appendToResponse)) {
                    return Result.success(cachedMovie)
                }
            }

            val result =
                retry {
                    api.getMovieDetails(movieId, language, buildAppendQuery(appendToResponse))
                }.onSuccessCatching {
                    cacheRepository.saveMovieToCache(
                        it.toDomain(),
                        language,
                        appendToResponse,
                    )
                }.toResult { it.toDomain() }
            return result
        }

        override suspend fun getSeriesDetails(
            seriesId: Int,
            language: String,
            appendToResponse: List<SeriesAppendedToResponse>,
        ): Result<SeriesDetail> {
            val cachedMovie = cacheRepository.getSeries(seriesId, language, appendToResponse).getOrNull()
            if (cachedMovie != null && isSeriesComplete(cachedMovie, appendToResponse)) {
                return Result.success(cachedMovie)
            }

            val result =
                retry {
                    api.getSerialDetails(seriesId, language, buildAppendQuery(appendToResponse))
                }.onSuccessCatching {
                    cacheRepository.saveSeriesToCache(it.toDomain(), language, appendToResponse)
                }.toResult { it.toDomain() }
            return result
        }

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
                        request = { page ->
                            retry { api.multiSearch(query, page, includeAdult, language, region) }
                                .toResult { it }
                                .getOrThrow()
                        },
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
            retry {
                api
                    .getProfileDetail(
                        profileId,
                        language,
                        appendToResponse,
                    )
            }.toResult {
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

        private fun isSeriesComplete(
            series: SeriesDetail,
            required: List<SeriesAppendedToResponse>,
        ): Boolean =
            required.all { type ->
                when (type) {
                    SeriesAppendedToResponse.AGGREGATE_CREDITS -> series.aggregateCredits != null
                    SeriesAppendedToResponse.VIDEOS -> series.videos != null
                }
            }
    }
