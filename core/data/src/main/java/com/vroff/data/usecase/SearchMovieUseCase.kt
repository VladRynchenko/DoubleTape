package com.vroff.data.usecase

import com.vroff.domain.models.Show
import com.vroff.domain.repository.ShowRepository
import com.vroff.domain.util.Resource
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(val repository: ShowRepository) {
    suspend fun execute(title: String): Resource<List<Show>> {
        return repository.getShowsByTitle(title)
    }
}