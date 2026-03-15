package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.SeriesCrew
import com.vroff.tmdb.entity.common.JobDTO

data class SeriesCrewDTO(
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
    val jobs: List<JobDTO>,
    val department: String,
    @SerializedName("total_episode_count")
    val totalEpisodeCount: Long,
) {
    fun mapToDomain(): SeriesCrew =
        SeriesCrew(
            adult = adult,
            gender = gender,
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profilePath = profilePath,
            jobs = jobs.map { it.mapToDomain() },
            department = department,
            totalEpisodeCount = totalEpisodeCount,
        )
}
