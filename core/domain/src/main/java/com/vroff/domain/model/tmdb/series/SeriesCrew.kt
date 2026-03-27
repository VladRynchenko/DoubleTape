package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.BaseCrew

data class SeriesCrew(
    override val adult: Boolean,
    override val gender: Long,
    override val id: Long,
    override val knownForDepartment: String,
    override val name: String,
    override val originalName: String,
    override val popularity: Double,
    override val profileImage: ProfileImage?,
    override val jobs: List<Job>,
    override val department: String,
    override val totalEpisodeCount: Int,
) : BaseCrew(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profileImage = profileImage,
        jobs = jobs,
        department = department,
        totalEpisodeCount = totalEpisodeCount,
    )
