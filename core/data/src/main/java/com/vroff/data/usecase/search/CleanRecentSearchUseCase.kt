package com.vroff.data.usecase.search

import com.vroff.domain.repository.RecentSearchRepository
import javax.inject.Inject

class CleanRecentSearchUseCase
    @Inject
    constructor(
        private val recentSearchRepository: RecentSearchRepository,
    ) {
        suspend operator fun invoke() = recentSearchRepository.clearRecentSearch()
    }
