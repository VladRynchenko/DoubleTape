package com.vroff.moviedd.domain

import com.vroff.moviedd.domain.models.Show

sealed class ShowState {
    data object Loading : ShowState()
    data object Waiting : ShowState()
    data class Success(val showList: List<Show>) : ShowState()
    data class Error(val error: String?) : ShowState()
}