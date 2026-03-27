package com.vroff.tmdb.entity.movie

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.movie.Crew

data class CrewDTO(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("credit_id")
    val creditId: String,
    val department: String,
    val job: String,
) {
    fun mapToDomain(): Crew =
        Crew(
            adult = adult,
            gender = gender,
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profileImage = profilePath?.let { ProfileImage(it) },
            creditId = creditId,
            department = department,
            job = job,
        )
}
