package com.vroff.data.usecase

import com.vroff.domain.models.Show
import com.vroff.domain.repository.ShowRepository
import com.vroff.domain.util.Resource
import javax.inject.Inject

class GetShowByIdUseCase @Inject constructor(private val repository: ShowRepository) {
    suspend fun execute(id: String): Resource<Show> {
        return repository.getShow(id)
    }
}