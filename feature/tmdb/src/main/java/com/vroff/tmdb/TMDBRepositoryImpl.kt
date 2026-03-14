package com.vroff.tmdb

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vroff.domain.model.streaming_available.NetworkResult
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.search.SearchResult
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.domain.repository.TMDBRepository
import com.vroff.domain.util.safeApiCall
import com.vroff.tmdb.api.TMDBApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val api: TMDBApi
) : TMDBRepository {

    override suspend fun getMovieDetails(
        movieId: String,
        language: String,
        appendToResponse: String
    ): NetworkResult<MovieDetail> {
        return api.getMovieDetails(movieId, language, appendToResponse)
            .safeApiCall { it.mapToDomain() }
    }

    override suspend fun getSeriesDetails(
        seriesId: String,
        language: String,
        appendToResponse: String
    ): NetworkResult<SeriesDetail> {
        return api.getSerialDetails(seriesId, language, appendToResponse)
            .safeApiCall { it.mapToDomain() }
    }

    override suspend fun multiSearch(
        query: String,
        page: Int,
        includeAdult: Boolean,
        language: String,
        region: String
    ): Flow<PagingData<SearchResult>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = {
                BasePagingSource(
                    request = { api.multiSearch(query, it, includeAdult, language, region) },
                    mapper = { it.mapToDomain() }
                )
            }
        ).flow
    }
}