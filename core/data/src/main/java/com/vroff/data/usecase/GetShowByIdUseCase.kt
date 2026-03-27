package com.vroff.data.usecase

import android.util.Log
import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.common.buildAppendQuery
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
    ) {
        suspend fun execute(
            id: Int,
            type: MediaType,
        ): NetworkResult<BaseDetails> =
            try {
                when (type) {
                    MediaType.MOVIE ->
                        repository.getMovieDetails(
                            id,
                            language = Locale.getDefault().language,
                            appendToResponse =
                                buildAppendQuery(
                                    MovieAppendedToResponse.CREDITS,
                                ),
                        )

                    MediaType.SERIES ->
                        repository.getSeriesDetails(
                            id,
                            language = Locale.getDefault().language,
                            appendToResponse =
                                buildAppendQuery(
                                    SeriesAppendedToResponse.AGGREGATE_CREDITS,
                                ),
                        )

                    else -> throw IllegalArgumentException("Unknown type")
                }
            } catch (e: Exception) {
                Log.e("Mapping", e.message.toString())
                NetworkResult.Exception(e)
            }
    }
