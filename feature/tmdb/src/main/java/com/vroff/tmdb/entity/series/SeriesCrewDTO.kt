package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.series.SeriesCrew
import com.vroff.tmdb.entity.common.JobDTO

data class SeriesCrewDTO(
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
    val jobs: List<JobDTO>,
    val department: String,
    @SerializedName("total_episode_count")
    val totalEpisodeCount: Int,
) {
    fun mapToDomain(): SeriesCrew =
        SeriesCrew(
            adult = adult,
            gender = Gender.entries[gender],
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profileImage = profilePath?.let { ProfileImage(it) },
            jobs = jobs.map { it.mapToDomain() },
            department = department,
            totalEpisodeCount = totalEpisodeCount,
        )
}
