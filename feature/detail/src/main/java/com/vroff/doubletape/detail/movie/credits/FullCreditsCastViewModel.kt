package com.vroff.doubletape.detail.movie.credits

import androidx.lifecycle.ViewModel
import com.vroff.doubletape.detail.profile.CreditType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FullCreditsCastViewModel
    @Inject
    constructor() : ViewModel() {
        val selectedTab = MutableStateFlow(CreditType.Cast)

        fun onTabSelected(tab: CreditType) {
            selectedTab.value = tab
        }
    }
