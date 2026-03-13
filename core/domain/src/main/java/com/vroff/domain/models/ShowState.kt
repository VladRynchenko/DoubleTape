package com.vroff.domain.models

sealed class ShowState {
    data object Loading : ShowState()
    data object Waiting : ShowState()
    data class Success(val showList: List<Show>) : ShowState()
    data class Error(val error: String?) : ShowState()
}