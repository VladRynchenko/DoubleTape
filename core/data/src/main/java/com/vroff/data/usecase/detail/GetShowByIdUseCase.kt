package com.vroff.data.usecase.detail

import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.movie.MovieAppendedToResponse
import com.vroff.domain.model.tmdb.movie.SeriesAppendedToResponse
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.repository.TMDBRepository
import java.util.Locale
import javax.inject.Inject

class GetShowByIdUseCase
    @Inject
    constructor(
        private val repository: TMDBRepository,
        private val locale: Locale,
    ) {
        suspend fun execute(
            id: Int,
            type: MediaType,
        ): Result<BaseDetails> =
            when (type) {
                MediaType.MOVIE ->
                    repository.getMovieDetails(
                        id,
                        language = locale.language,
                        appendToResponse =
                            listOf(
                                MovieAppendedToResponse.CREDITS,
                                MovieAppendedToResponse.VIDEOS,
                            ),
                    )

                MediaType.SERIES ->
                    repository.getSeriesDetails(
                        id,
                        language = locale.language,
                        appendToResponse =
                            listOf(
                                SeriesAppendedToResponse.AGGREGATE_CREDITS,
                                SeriesAppendedToResponse.VIDEOS,
                            ),
                    )

                else -> Result.failure(IllegalArgumentException("Unknown type"))
            }

        suspend fun executeRefresh(
            id: Int,
            type: MediaType,
        ): Result<BaseDetails> =
            when (type) {
                MediaType.MOVIE ->
                    repository.getMovieNetwork(
                        id,
                        language = Locale.getDefault().language,
                        appendToResponse =
                            listOf(
                                MovieAppendedToResponse.CREDITS,
                                MovieAppendedToResponse.VIDEOS,
                            ),
                    )

                MediaType.SERIES ->
                    repository.getSeriesNetwork(
                        id,
                        language = Locale.getDefault().language,
                        appendToResponse =
                            listOf(
                                SeriesAppendedToResponse.AGGREGATE_CREDITS,
                                SeriesAppendedToResponse.VIDEOS,
                            ),
                    )

                else -> Result.failure(IllegalArgumentException("Unknown type"))
            }
    }
