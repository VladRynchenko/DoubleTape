package com.vroff.streamingmovie.usecase

import android.util.Log
import com.vroff.domain.model.streaming_available.NetworkResult
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.repository.TMDBRepository
import javax.inject.Inject

class GetShowByIdUseCase @Inject constructor(private val repository: TMDBRepository) {
    suspend fun execute(id: String): NetworkResult<MovieDetail> {
        return try {
            repository.getMovieDetails(id)
        } catch (e: Exception) {
            Log.e("Mapping", e.message.toString())
            NetworkResult.Exception(e)
        }
    }
}