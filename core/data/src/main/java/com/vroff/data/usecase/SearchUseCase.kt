package com.vroff.data.usecase

import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.SearchResult
import com.vroff.domain.repository.TMDBRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class SearchUseCase @Inject constructor(val repository: TMDBRepository, val locale: Locale) {
    suspend fun execute(title: String): Flow<PagingData<SearchResult>> {
        return repository.multiSearch(
            title,
            false,
            locale.language,
            locale.country
        )
    }
}