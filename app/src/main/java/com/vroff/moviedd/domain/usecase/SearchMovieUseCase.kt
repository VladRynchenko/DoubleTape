package com.vroff.moviedd.domain.usecase

import com.vroff.moviedd.domain.models.Show
import com.vroff.moviedd.domain.repository.ShowRepository
import com.vroff.moviedd.domain.util.Resource
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(val repository: ShowRepository) {
    suspend fun execute(title: String): Resource<List<Show>> {
        return repository.getShowsByTitle(title)
    }
}