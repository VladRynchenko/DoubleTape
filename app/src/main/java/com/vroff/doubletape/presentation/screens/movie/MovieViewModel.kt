package com.vroff.doubletape.presentation.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.GetShowByIdUseCase
import com.vroff.domain.model.streaming_available.NetworkResult
import com.vroff.domain.model.tmdb.movie.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var getShowByIdUseCase: GetShowByIdUseCase

    private val _tmdbIdStateFlow = MutableStateFlow("")
    var showState = _tmdbIdStateFlow
        .filter { it != "" }
        .map { id ->
            when (val result = getShowByIdUseCase.execute(id)) {
                is NetworkResult.Success -> ScreenState.Success(data = result.data)
                is NetworkResult.Error -> ScreenState.Error(result.message)
                is NetworkResult.Exception -> ScreenState.Error(result.e.message)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ScreenState.Loading
        )

    fun setTMDBId(id: String?) {
        if (!id.isNullOrBlank()) {
            _tmdbIdStateFlow.value = id
        } else {
            _tmdbIdStateFlow.value = ""
        }
    }


    sealed class ScreenState {
        data class Success(val data: MovieDetail) : ScreenState()
        data class Error(val e: String?) : ScreenState()
        data object Loading : ScreenState()
    }
}