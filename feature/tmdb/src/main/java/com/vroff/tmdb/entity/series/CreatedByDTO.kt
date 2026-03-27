package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.series.CreatedBy

data class CreatedByDTO(
    val id: Int,
    @SerializedName("credit_id")
    val creditId: String,
    val name: String,
    val gender: Int,
    @SerializedName("profile_path")
    val profilePath: String?,
) {
    fun mapToDomain(): CreatedBy =
        CreatedBy(
            id = id,
            creditId = creditId,
            name = name,
            gender = gender,
            profileImage = profilePath?.let { ProfileImage(it) },
        )
}
