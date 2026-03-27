package com.vroff.tmdb.entity.movie

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Cast
import com.vroff.domain.model.tmdb.search.Gender

data class CastDTO(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("cast_id")
    val castId: Int,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val order: Int,
) {
    fun mapToDomain(): Cast =
        Cast(
            adult = adult,
            gender = Gender.entries[gender],
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profileImage = profilePath?.let { ProfileImage(it) },
            castId = castId,
            character = character,
            creditId = creditId,
            order = order,
        )
}
