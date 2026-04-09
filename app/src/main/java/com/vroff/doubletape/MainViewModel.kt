package com.vroff.doubletape

import androidx.compose.foundation.text.input.TextFieldState
import com.vroff.ui.model.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor() : BaseViewModel() {
        private val _query = MutableStateFlow(TextFieldState())
        val query = _query.asStateFlow()
    }
