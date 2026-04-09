package com.vroff.ui.model

sealed class UiEvent {
    data class ShowToast(
        val message: String,
    ) : UiEvent()
}
