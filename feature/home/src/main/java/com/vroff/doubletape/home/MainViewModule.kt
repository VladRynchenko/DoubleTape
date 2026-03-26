package com.vroff.doubletape.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.GetMainScreenDataUseCase
import com.vroff.domain.model.home.MainScreenContent
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModule
    @Inject
    constructor(
        private val getMainScreenDataUseCase: GetMainScreenDataUseCase,
    ) : ViewModel() {
        init {
            invokeInitData()
        }

        private val _nowPlayingMovieFlow = MutableStateFlow<List<MovieMediaItem>>(emptyList())
        val nowPlayingMovieFlow: StateFlow<List<MovieMediaItem>>
            get() = _nowPlayingMovieFlow.asStateFlow()

        private val _mainScreenContent = MutableStateFlow<MainScreenContent?>(null)
        val mainScreenContent: StateFlow<MainScreenContent?>
            get() = _mainScreenContent.asStateFlow()

        fun invokeInitData() {
            viewModelScope.launch {
                val state = getMainScreenDataUseCase.execute()
                _mainScreenContent.value = state
            }
        }
    }
