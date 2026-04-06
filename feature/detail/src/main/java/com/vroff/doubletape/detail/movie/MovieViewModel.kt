package com.vroff.doubletape.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.detail.GetShowByIdUseCase
import com.vroff.domain.model.tmdb.common.BaseCredits
import com.vroff.domain.model.tmdb.common.VideoData
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.ui.model.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
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
                .flatMapLatest { (id, type) ->
                    flow {
                        val result = getShowByIdUseCase.execute(id, type)
                        emit(
                            result.fold(
                                onSuccess = { BaseScreenState.Success(it) },
                                onFailure = { BaseScreenState.Error(it.message) },
                            ),
                        )
                    }
                }.onEach { state ->
                    if (state is BaseScreenState.Success) {
                        val data = state.data
                        val credit: BaseCredits =
                            when (data) {
                                is MovieDetail -> data.credits
                                is SeriesDetail -> data.aggregateCredits
                                else -> emptyList<BaseCredits>()
                            } as BaseCredits

                        videoStateFlow.update { data.videos ?: emptyList() }
                        creditsStateFlow.update { credit }
                    }
                }.stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    BaseScreenState.Loading,
                )

        val videoStateFlow = MutableStateFlow<List<VideoData>>(emptyList())
        val creditsStateFlow = MutableStateFlow(BaseCredits(emptyList(), emptyList()))

        fun setTMDBId(
            id: Int,
            type: MediaType,
        ) {
            tmdbIdStateFlow.tryEmit(id to type)
        }
    }
