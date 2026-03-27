package com.vroff.tmdb.entity.profile

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.profile.ProfileDetail
import com.vroff.domain.model.tmdb.search.Gender

data class ProfileDetailDTO(
    val adult: Boolean,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String?,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    val name: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    val popularity: Float,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("external_ids")
    val externalIds: ExternalIdsDTO?,
    @SerializedName("combined_credits")
    val combinedCreditsDTO: CombinedCreditsDTO?,
) {
    fun mapToDomain(): ProfileDetail =
        ProfileDetail(
            adult = adult,
            biography = biography,
            birthday = birthday,
            deathday = deathday,
            gender = Gender.entries[gender],
            homepage = homepage,
            id = id,
            imdbId = imdbId,
            knownForDepartment = knownForDepartment,
            name = name,
            placeOfBirth = placeOfBirth,
            popularity = popularity,
            profileImage = profilePath?.let { ProfileImage(it) },
            externalIds = externalIds?.mapToDomain(),
            combinedCredits = combinedCreditsDTO?.mapToDomain(),
        )
}
