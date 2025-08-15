package com.vroff.moviedd.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.moviedd.domain.ShowState
import com.vroff.moviedd.domain.usecase.SearchMovieUseCase
import com.vroff.moviedd.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val stateFlow = searchQuery
        .debounce(300L)
        .filter { query -> query.length >= 2 }
        .distinctUntilChanged()
        .map { query ->
            when (val result = searchMovieUseCase.execute(title = query)) {
                is Resource.Error -> ShowState.Error(error = result.message)
                is Resource.Success -> ShowState.Success(showList = result.data ?: arrayListOf())
            }
        }
        .onStart { emit(ShowState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ShowState.Waiting
        )

    fun setSearchQuery(searchQuery: String) {
        _searchQuery.update { searchQuery }
    }
}