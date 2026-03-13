package com.vroff.domain.model.streaming_available

sealed class ShowState {
    data object Loading : ShowState()
    data object Waiting : ShowState()
    data class Success(val showList: List<Show>) : ShowState()
    data class Error(val error: String?) : ShowState()
}