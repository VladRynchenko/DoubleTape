package com.vroff.doubletape.detail.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.GetProfileUseCase
import com.vroff.domain.model.NetworkResult
import com.vroff.ui.model.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        val getProfileUseCase: GetProfileUseCase,
    ) : ViewModel() {
        private val profileIdStateFlow = MutableStateFlow(-1)

        val state =
            profileIdStateFlow
                .map { profileId ->
                    when (val profileDetail = getProfileUseCase.execute(profileId)) {
                        is NetworkResult.Success -> BaseScreenState.Success(profileDetail.data)
                        is NetworkResult.Error -> BaseScreenState.Error(profileDetail.message)
                        is NetworkResult.Exception -> BaseScreenState.Error(profileDetail.e.message)
                    }
                }.stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    BaseScreenState.Loading,
                )

        fun setProfileId(profileId: Int) {
            profileIdStateFlow.update { profileId }
        }
    }
