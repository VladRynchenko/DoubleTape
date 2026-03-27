package com.vroff.data.usecase.search

import com.vroff.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchUseCase
    @Inject
    constructor(
        private val recentSearchRepository: RecentSearchRepository,
    ) {
        operator fun invoke(): Flow<List<String>> = recentSearchRepository.getRecentSearches()
    }
