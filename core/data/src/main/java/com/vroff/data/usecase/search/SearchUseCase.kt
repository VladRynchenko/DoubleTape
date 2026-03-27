package com.vroff.data.usecase.search

import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.TMDBMediaItem
import com.vroff.domain.repository.TMDBRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class SearchUseCase
    @Inject
    constructor(
        val repository: TMDBRepository,
        val locale: Locale,
    ) {
        fun execute(title: String): Flow<PagingData<TMDBMediaItem>> =
            repository.multiSearch(
                title,
                false,
                locale.language,
                locale.country,
            )
    }
