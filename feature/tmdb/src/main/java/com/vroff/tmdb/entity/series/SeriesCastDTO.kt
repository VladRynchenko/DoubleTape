package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.SeriesCast
import com.vroff.tmdb.entity.common.RoleDTO

data class SeriesCastDTO(
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
    val roles: List<RoleDTO?>? = null,
    @SerializedName("total_episode_count")
    val totalEpisodeCount: Long,
    val order: Long,
) {
    fun mapToDomain(): SeriesCast =
        SeriesCast(
            adult = adult,
            gender = gender,
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profilePath = profilePath,
            roles = roles?.map { it?.mapToDomain() },
            totalEpisodeCount = totalEpisodeCount,
            order = order,
        )
}
