package com.vroff.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.vroff.data.usecase.SearchUseCase
import com.vroff.domain.model.tmdb.search.typed.TypedSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchUseCase: SearchUseCase,
    ) : ViewModel() {
        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        val pagingFlow: Flow<PagingData<TypedSearchResult>> =
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.length < 2) {
                        flowOf(PagingData.empty())
                    } else {
                        searchUseCase
                            .execute(query)
                            .mapNotNull { item ->
                                val observedIds = mutableSetOf<String>()
                                item.map { it.mapToTypedResult() as TypedSearchResult }.filter { item ->
                                    val id = "${item.mediaType}/${item.id}"
                                    val isUnique = !observedIds.contains(id)
                                    if (isUnique) observedIds.add(id)
                                    isUnique
                                }
                            }
                    }
                }.cachedIn(viewModelScope)

        fun setSearchQuery(searchQuery: String) {
            _searchQuery.update { searchQuery }
        }
    }

sealed class SearchScreenState {
    data object Loading : SearchScreenState()

    data object Waiting : SearchScreenState()

    data object Success : SearchScreenState()

    data object Empty : SearchScreenState()

    data class Error(
        val e: String,
    ) : SearchScreenState()
}
