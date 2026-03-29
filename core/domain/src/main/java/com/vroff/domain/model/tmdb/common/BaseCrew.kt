package com.vroff.domain.model.tmdb.common

import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.series.Job

open class BaseCrew(
    open val adult: Boolean,
    open val gender: Gender,
    open val id: Int,
    open val knownForDepartment: String,
    open val name: String,
    open val originalName: String,
    open val popularity: Double,
    open val profileImage: ProfileImage?,
    open val creditId: String? = null,
    open val department: String,
    open val job: String? = null,
    open val jobs: List<Job>? = null,
    open val totalEpisodeCount: Int? = null,
)
