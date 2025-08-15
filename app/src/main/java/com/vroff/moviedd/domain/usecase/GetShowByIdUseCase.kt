package com.vroff.moviedd.domain.usecase

import com.vroff.moviedd.domain.models.Show
import com.vroff.moviedd.domain.repository.ShowRepository
import com.vroff.moviedd.domain.util.Resource
import javax.inject.Inject

class GetShowByIdUseCase @Inject constructor(private val repository: ShowRepository) {
    suspend fun execute(id: String): Resource<Show> {
        return repository.getShow(id)
    }
}