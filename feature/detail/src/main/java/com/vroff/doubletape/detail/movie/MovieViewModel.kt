package com.vroff.doubletape.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.GetShowByIdUseCase
import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.search.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor() : ViewModel() {
        @Inject
        lateinit var getShowByIdUseCase: GetShowByIdUseCase

        private val tmdbIdStateFlow = MutableStateFlow(-1 to MediaType.UNKNOWN)
        val showState =
            tmdbIdStateFlow
                .filter { it.first != -1 }
                .map { (id, type) ->
                    when (val result = getShowByIdUseCase.execute(id, type)) {
                        is NetworkResult.Success -> ScreenState.Success(data = result.data)
                        is NetworkResult.Error -> ScreenState.Error(result.message)
                        is NetworkResult.Exception -> ScreenState.Error(result.e.message)
                    }
                }.stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    ScreenState.Loading,
                )

        fun setTMDBId(
            id: Int,
            type: MediaType,
        ) {
            tmdbIdStateFlow.tryEmit(id to type)
        }

        sealed class ScreenState {
            data class Success(
                val data: BaseDetails,
            ) : ScreenState()

            data class Error(
                val e: String?,
            ) : ScreenState()

            data object Loading : ScreenState()
        }
    }
