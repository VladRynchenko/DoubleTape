package com.vroff.ui.model

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object EventManager {
    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun send(event: UiEvent) {
        _events.trySend(event)
    }
}
