package com.vroff.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vroff.data.usecase.search.AddRecentSearchUseCase
import com.vroff.data.usecase.search.CleanRecentSearchUseCase
import com.vroff.data.usecase.search.GetRecentSearchUseCase
import com.vroff.data.usecase.search.SearchUseCase
import com.vroff.domain.model.tmdb.search.typed.TypedSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val searchUseCase: SearchUseCase,
        private val addRecentSearchUseCase: AddRecentSearchUseCase,
        getRecentSearchUseCase: GetRecentSearchUseCase,
        private val cleanRecentSearchUseCase: CleanRecentSearchUseCase,
    ) : ViewModel() {
        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        val recentSearches: StateFlow<List<String>?> =
            getRecentSearchUseCase
                .invoke()
                .stateIn(viewModelScope, SharingStarted.Lazily, null)

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
                                item.map { it.mapToTypedResult() as TypedSearchResult }
                            }
                    }
                }.cachedIn(viewModelScope)

        fun setSearchQuery(searchQuery: String) {
            _searchQuery.update { searchQuery }
        }

        fun addRecentSearch(query: String) {
            viewModelScope.launch {
                addRecentSearchUseCase.invoke(query)
            }
        }

        fun clearRecentSearches() {
            viewModelScope.launch {
                cleanRecentSearchUseCase.invoke()
            }
        }
    }

sealed class SearchScreenState {
    data object Loading : SearchScreenState()

    data class Waiting(
        val list: List<String>?,
    ) : SearchScreenState()

    data object Success : SearchScreenState()

    data object Empty : SearchScreenState()

    data class Error(
        val e: String,
    ) : SearchScreenState()
}
