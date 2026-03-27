package com.vroff.data.usecase.category

import com.vroff.domain.repository.TrendingRepository
import javax.inject.Inject

class GetUpcomingMovieUseCase
    @Inject
    constructor(
        private val repository: TrendingRepository,
    )
