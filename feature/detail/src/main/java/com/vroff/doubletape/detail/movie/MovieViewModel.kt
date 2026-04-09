package com.vroff.doubletape.detail.movie

import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.detail.GetShowByIdUseCase
import com.vroff.domain.model.tmdb.common.BaseCredits
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.ui.model.BaseScreenState
import com.vroff.ui.model.BaseViewModel
import com.vroff.ui.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieViewModel
    @Inject
    constructor() : BaseViewModel() {
        @Inject
        lateinit var getShowByIdUseCase: GetShowByIdUseCase

        private val _showState = MutableStateFlow<BaseScreenState<BaseDetails>>(BaseScreenState.Loading)
        val showState = _showState.asStateFlow()

        fun refresh(
            id: Int,
            type: MediaType,
        ) {
            refresh {
                val result = getShowByIdUseCase.executeRefresh(id, type)
                result.onSuccess {
                    _showState.emit(BaseScreenState.Success(it))
                }
                result.onFailure {
                    send(UiEvent.ShowToast(it.message ?: "Unknown error"))
                }
            }
        }

        fun load(
            id: Int,
            type: MediaType,
        ) {
            viewModelScope.launch {
                val result = getShowByIdUseCase.execute(id, type)
                _showState.value =
                    result.fold(
                        onSuccess = { BaseScreenState.Success(it) },
                        onFailure = { BaseScreenState.Error(it.message) },
                    )
            }
        }

        val videoStateFlow =
            _showState
                .map { state ->
                    (state as BaseScreenState.Success).data.videos ?: emptyList()
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        val creditsStateFlow =
            _showState
                .map { state ->
                    when (val data = (state as BaseScreenState.Success).data) {
                        is MovieDetail -> data.credits
                        is SeriesDetail -> data.aggregateCredits
                        else -> BaseCredits(emptyList(), emptyList())
                    }
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BaseCredits(emptyList(), emptyList()))
    }
