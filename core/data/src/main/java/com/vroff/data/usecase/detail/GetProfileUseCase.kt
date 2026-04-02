package com.vroff.data.usecase.detail

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
        private val locale: Locale,
    ) {
        suspend fun execute(profileId: Int): Result<ProfileDetail> =
            profileRepository.getProfile(
                profileId,
                locale.language,
                appendToResponse =
                    buildAppendQuery(
                        listOf(
                            PersonAppendToResponse.EXTERNAL_IDS,
                            PersonAppendToResponse.COMBINED_CREDITS,
                        ),
                    ),
            )
    }
