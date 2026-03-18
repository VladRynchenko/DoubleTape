package com.vroff.doubletape

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor() : ViewModel() {
        //    @Inject
//    lateinit var getConfigurationUseCase: GetConfigurationUseCase
//
//    init {
//        viewModelScope.launch {
//            getConfigurationUseCase.execute()
//        }
//    }

        private val _query = MutableStateFlow(TextFieldState())
        val query = _query.asStateFlow()

        fun clearQuery() {
            _query.value.clearText()
        }
    }
