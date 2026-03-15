package com.vroff.doubletape

import androidx.compose.ui.text.input.TextFieldValue
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

        private val _query = MutableStateFlow(TextFieldValue())
        val query = _query.asStateFlow()

        fun clearQuery() {
            _query.value = TextFieldValue()
        }

        fun setQuery(newQuery: TextFieldValue) {
            _query.value = newQuery
        }
    }
