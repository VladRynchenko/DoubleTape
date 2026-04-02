package com.vroff.tmdb

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import com.vroff.domain.repository.TrendingRepository
import com.vroff.domain.util.getOrNull
import com.vroff.domain.util.safeApiCall
import com.vroff.network.paging.TimedPagingSource
import com.vroff.tmdb.api.TrendingApi
import com.vroff.tmdb.entity.common.GenreMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingRepositoryImpl
    @Inject
    constructor(
        val trendingApi: TrendingApi,
        val genreMapper: GenreMapper,
    ) : TrendingRepository {
        override suspend fun getNowPlayingMoviePreview(
            language: String?,
            region: String?,
        ): Result<List<MovieMediaItem>> =
            trendingApi.getNowPlayingMovie(1, language, region).safeApiCall { pagedData ->
                pagedData.results.map {
                    it.mapToDomain(genreMapper::map).toMovie()
                }
            }

        override fun getNowPlayingMovie(
            language: String?,
            region: String?,
        ): Flow<PagingData<MovieMediaItem>> =
            Pager(
                config = PagingConfig(20),
                pagingSourceFactory = {
                    TimedPagingSource(
                        request = { page ->
                            trendingApi.getNowPlayingMovie(page, language, region).safeApiCall { it }.getOrNull()
                        },
                        mapper = {
                            it.mapToDomain(genreMapper::map).toMovie()
                        },
                    )
                },
            ).flow

        override suspend fun getUpcomingMoviePreview(
            language: String?,
            region: String?,
        ): Result<List<MovieMediaItem>> =
            trendingApi.getUpcomingMovie(1, language, region).safeApiCall { pagedData ->
                pagedData.results.map { it.mapToDomain(genreMapper::map).toMovie() }
            }

        override suspend fun getPopularMoviePreview(
            language: String?,
            region: String?,
        ): Result<List<MovieMediaItem>> =
            trendingApi.getPopularMovie(1, language, region).safeApiCall { pagedData ->
                pagedData.results.map { it.mapToDomain(genreMapper::map).toMovie() }
            }
    }
