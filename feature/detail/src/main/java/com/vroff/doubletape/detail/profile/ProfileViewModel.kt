package com.vroff.doubletape.detail.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroff.data.usecase.detail.GetProfileUseCase
import com.vroff.domain.model.NetworkResult
import com.vroff.ui.model.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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

        private val _selectedTab = MutableStateFlow(CreditType.Cast)
        val selectedTab = _selectedTab.asStateFlow()

        private val _isExpanded = MutableStateFlow(false)
        val isExpanded = _isExpanded.asStateFlow()

        val availableTabs: StateFlow<List<CreditType>> =
            state
                .map { screenState ->
                    if (screenState is BaseScreenState.Success) {
                        val credits = screenState.data.combinedCredits
                        buildList {
                            if (credits?.cast?.isNotEmpty() == true) {
                                add(CreditType.Cast)
                            }
                            if (credits?.crew?.isNotEmpty() == true) {
                                add(CreditType.Crew)
                            }
                        }
                    } else {
                        emptyList()
                    }
                }.onEach { tabs ->
                    if (tabs.isNotEmpty() && _selectedTab.value !in tabs) {
                        _selectedTab.value = tabs.first()
                    }
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        fun onTabSelected(tab: CreditType) {
            _selectedTab.value = tab
            _isExpanded.value = false
        }

        fun toggleExpanded() {
            _isExpanded.value = !_isExpanded.value
        }

        fun setProfileId(profileId: Int) {
            profileIdStateFlow.update { profileId }
        }
    }
