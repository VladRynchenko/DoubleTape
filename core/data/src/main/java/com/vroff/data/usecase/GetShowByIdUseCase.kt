package com.vroff.data.usecase

import android.util.Log
import com.vroff.domain.model.streaming_available.NetworkResult
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.repository.TMDBRepository
import java.util.Locale
import javax.inject.Inject

class GetShowByIdUseCase @Inject constructor(private val repository: TMDBRepository) {
    suspend fun execute(id: String): NetworkResult<MovieDetail> {
        return try {
            repository.getMovieDetails(
                id,
                language = Locale.getDefault().language ,
                appendToResponse = "credits"
            )
        } catch (e: Exception) {
            Log.e("Mapping", e.message.toString())
            NetworkResult.Exception(e)
        }
    }
}