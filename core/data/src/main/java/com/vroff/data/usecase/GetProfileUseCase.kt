package com.vroff.data.usecase

import com.vroff.domain.model.NetworkResult
import com.vroff.domain.model.tmdb.common.buildAppendQuery
import com.vroff.domain.model.tmdb.profile.PersonAppendToResponse
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.repository.TMDBRepository
import java.util.Locale
import javax.inject.Inject

class GetProfileUseCase
    @Inject
    constructor(
        private val profileRepository: TMDBRepository,
    ) {
        suspend fun execute(profileId: Int): NetworkResult<ProfileDetail> =
            profileRepository.getProfile(
                profileId,
                Locale.getDefault().language,
                appendToResponse =
                    buildAppendQuery(
                        PersonAppendToResponse.EXTERNAL_IDS,
                        PersonAppendToResponse.COMBINED_CREDITS,
                    ),
            )
    }
