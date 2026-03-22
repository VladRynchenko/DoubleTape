package com.vroff.domain.model.tmdb.profile

import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.search.Gender

data class ProfileDetail(
    val adult: Boolean,
    val biography: String,
    val birthday: String?,
    val deathday: String?,
    val gender: Gender,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Float,
    val profileImage: ProfileImage?,
    val externalIds: ExternalIds?,
    val combinedCredits: CombinedCredits?,
)
