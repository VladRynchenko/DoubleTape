package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.SeriesCast
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.tmdb.entity.common.RoleDTO

data class SeriesCastDTO(
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
    val roles: List<RoleDTO?>? = null,
    @SerializedName("total_episode_count")
    val totalEpisodeCount: Int,
    val order: Int,
) {
    fun mapToDomain(): SeriesCast =
        SeriesCast(
            adult = adult,
            gender = Gender.entries[gender],
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profileImage = profilePath?.let { ProfileImage(it) },
            roles = roles?.map { it?.mapToDomain() },
            totalEpisodeCount = totalEpisodeCount,
            order = order,
        )
}
