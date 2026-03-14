package com.vroff.doubletape.presentation.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import com.vroff.domain.model.streaming_available.ShowState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
) : ViewModel() {

//    @Inject
//    lateinit var getShowByIdUseCase: GetShowByIdUseCase

    var showState by mutableStateOf<ShowState>(
        ShowState.Loading
    )
        private set

    fun getShows() {
        viewModelScope.launch {
//            showState = when (val result = showRepository.getShows()) {
//                is Resource.Error -> {
//                    ShowState.Error(
//                        error = result.message ?: "Error"
//                    )
//                }
//
//                is Resource.Success -> {
//                    ShowState.Success(
//                        showList = result.data ?: listOf()
//                    )
//                }
//            }
        }
    }

    fun getTopShows() {

    }

}