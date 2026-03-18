package com.vroff.data.usecase

import android.util.Log
import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.common.BaseDetails
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
                            appendToResponse = "credits",
                        )
                    MediaType.SERIES ->
                        repository.getSeriesDetails(
                            id,
                            language = Locale.getDefault().language,
                            appendToResponse = "aggregate_credits",
                        )
                    else -> throw IllegalArgumentException("Unknown type")
                }
            } catch (e: Exception) {
                Log.e("Mapping", e.message.toString())
                NetworkResult.Exception(e)
            }
    }
