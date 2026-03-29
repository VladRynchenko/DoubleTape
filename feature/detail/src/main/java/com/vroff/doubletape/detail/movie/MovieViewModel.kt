package com.vroff.doubletape.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.detail.GetShowByIdUseCase
import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.common.VideoData
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.ui.model.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
                        is NetworkResult.Success -> {
                            BaseScreenState.Success(data = result.data).also {
                                videoStateFlow.update { result.data.videos ?: emptyList() }
                            }
                        }
                        is NetworkResult.Error -> BaseScreenState.Error(result.message)
                        is NetworkResult.Exception -> BaseScreenState.Error("${type.type}/$id/ln${result.e.message}")
                    }
                }.stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    BaseScreenState.Loading,
                )

        val videoStateFlow = MutableStateFlow<List<VideoData>>(emptyList())

        fun setTMDBId(
            id: Int,
            type: MediaType,
        ) {
            tmdbIdStateFlow.tryEmit(id to type)
        }
    }
