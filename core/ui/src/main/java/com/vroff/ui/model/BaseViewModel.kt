package com.vroff.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val events = EventManager.events

    private val _isRefreshingFlow = MutableStateFlow(false)
    val isRefreshingFlow: StateFlow<Boolean>
        get() = _isRefreshingFlow.asStateFlow()

    protected fun refresh(block: suspend () -> Unit) {
        if (_isRefreshingFlow.value) return
        viewModelScope.launch {
            _isRefreshingFlow.value = true
            try {
                block()
            } finally {
                _isRefreshingFlow.value = false
            }
        }
    }

    fun send(event: UiEvent) {
        EventManager.send(event)
    }
}
