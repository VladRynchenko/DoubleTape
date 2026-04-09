package com.vroff.doubletape.home

import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.GetMainScreenDataUseCase
import com.vroff.domain.model.home.MainScreenContent
import com.vroff.ui.model.BaseScreenState
import com.vroff.ui.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getMainScreenDataUseCase: GetMainScreenDataUseCase,
    ) : BaseViewModel() {
        init {
            observeData()
        }

        private val _state = MutableStateFlow<BaseScreenState<MainScreenContent>>(BaseScreenState.Loading)
        val state: StateFlow<BaseScreenState<MainScreenContent>>
            get() = _state.asStateFlow()

        fun refresh() {
            refresh {
                getMainScreenDataUseCase.execute().first()
            }
        }

        fun observeData() {
            viewModelScope.launch {
                val state = getMainScreenDataUseCase.execute()
                state.collect { result ->
                    result.fold(
                        onSuccess = {
                            _state.value = BaseScreenState.Success(it)
                        },
                        onFailure = {
                            _state.value = BaseScreenState.Error(it.message)
                        },
                    )
                }
            }
        }
    }
