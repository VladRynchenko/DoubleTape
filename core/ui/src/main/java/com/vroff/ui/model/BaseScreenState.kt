package com.vroff.ui.model

sealed class BaseScreenState<out T> {
    data class Success<T>(
        val data: T,
    ) : BaseScreenState<T>()

    data class Error(
        val e: String?,
    ) : BaseScreenState<Nothing>()

    data object Loading : BaseScreenState<Nothing>()
}
