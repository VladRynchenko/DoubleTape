package com.vroff.tmdb

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import com.vroff.domain.repository.TrendingRepository
import com.vroff.domain.util.toResult
import com.vroff.network.paging.TimedPagingSource
import com.vroff.network.retry
import com.vroff.tmdb.api.TrendingApi
import com.vroff.tmdb.entity.common.GenreMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingRepositoryImpl
    @Inject
    constructor(
        val trendingApi: TrendingApi,
        val genreMapper: GenreMapper,
    ) : TrendingRepository {
        override fun getNowPlayingMoviePreview(
            language: String?,
            region: String?,
        ): Flow<Result<List<MovieMediaItem>>> =
            flow {
                val result =
                    trendingApi.getNowPlayingMovie(1, language, region).toResult { pagedData ->
                        pagedData.results.map {
                            it.mapToDomain(genreMapper::map).toMovie()
                        }
                    }
                emit(result)
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
                            retry { trendingApi.getNowPlayingMovie(page, language, region) }.toResult { it }.getOrNull()
                        },
                        mapper = {
                            it.mapToDomain(genreMapper::map).toMovie()
                        },
                    )
                },
            ).flow

        override fun getUpcomingMoviePreview(
            language: String?,
            region: String?,
        ): Flow<Result<List<MovieMediaItem>>> =
            flow {
                val result =
                    trendingApi.getUpcomingMovie(1, language, region).toResult { pagedData ->
                        pagedData.results.map { it.mapToDomain(genreMapper::map).toMovie() }
                    }
                emit(result)
            }

        override fun getPopularMoviePreview(
            language: String?,
            region: String?,
        ): Flow<Result<List<MovieMediaItem>>> =
            flow {
                val result =
                    trendingApi
                        .getPopularMovie(1, language, region)
                        .toResult { pagedData ->
                            pagedData.results.map { it.mapToDomain(genreMapper::map).toMovie() }
                        }
                emit(result)
            }
    }
