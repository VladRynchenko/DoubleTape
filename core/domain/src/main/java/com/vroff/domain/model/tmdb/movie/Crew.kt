package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.BaseCrew

data class Crew(
    override val adult: Boolean,
    override val gender: Long,
    override val id: Long,
    override val knownForDepartment: String,
    override val name: String,
    override val originalName: String,
    override val popularity: Double,
    override val profileImage: ProfileImage?,
    override val creditId: String,
    override val department: String,
    override val job: String,
) : BaseCrew(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profileImage = profileImage,
        creditId = creditId,
        department = department,
        job = job,
    )
