package com.vroff.tmdb

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.model.tmdb.search.TMDBMediaItem
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.domain.repository.TMDBRepository
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
    ) : TMDBRepository {
        override suspend fun getMovieDetails(
            movieId: Int,
            language: String,
            appendToResponse: String?,
        ): NetworkResult<MovieDetail> =
            api
                .getMovieDetails(movieId, language, appendToResponse)
                .safeApiCall { it.mapToDomain() }

        override suspend fun getSeriesDetails(
            seriesId: Int,
            language: String,
            appendToResponse: String?,
        ): NetworkResult<SeriesDetail> =
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
        ): NetworkResult<ProfileDetail> =
            api
                .getProfileDetail(
                    profileId,
                    language,
                    appendToResponse,
                ).safeApiCall {
                    it.mapToDomain()
                }
    }
