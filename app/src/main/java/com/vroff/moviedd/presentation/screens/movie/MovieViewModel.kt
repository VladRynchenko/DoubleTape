package com.vroff.moviedd.presentation.screens.movie

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.moviedd.domain.models.Show
import com.vroff.moviedd.domain.repository.ShowRepository
import com.vroff.moviedd.domain.usecase.GetShowByIdUseCase
import com.vroff.moviedd.domain.util.Resource
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
            ScreenState.Loading
            when (val result = getShowByIdUseCase.execute(id)) {
                is Resource.Error -> ScreenState.Error(result.message)
                is Resource.Success -> ScreenState.Success(result.data!!)
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
        data class Success(val data: Show) : ScreenState()
        data class Error(val e: String?) : ScreenState()
        data object Loading : ScreenState()
    }
}