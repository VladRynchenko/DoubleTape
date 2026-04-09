package com.vroff.domain.model.tmdb.common

import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.series.Role

sealed class CastBase(
    open val adult: Boolean,
    open val gender: Gender,
    open val id: Int,
    open val knownForDepartment: String,
    open val name: String,
    open val originalName: String,
    open val popularity: Double,
    open val profileImage: ProfileImage?,
    open val order: Int? = null,
    open val roles: List<Role>? = null,
    open val totalEpisodeCount: Int? = null,
    open val castId: Int? = null,
    open val character: String? = null,
    open val creditId: String? = null,
)

data class Cast(
    override val adult: Boolean,
    override val gender: Gender,
    override val id: Int,
    override val knownForDepartment: String,
    override val name: String,
    override val originalName: String,
    override val popularity: Double,
    override val profileImage: ProfileImage?,
    override val castId: Int,
    override val character: String,
    override val creditId: String,
    override val order: Int,
) : CastBase(
        id = id,
        name = name,
        profileImage = profileImage,
        character = character,
        creditId = creditId,
        order = order,
        adult = adult,
        gender = gender,
        knownForDepartment = knownForDepartment,
        originalName = originalName,
        popularity = popularity,
    )

data class SeriesCast(
    override val adult: Boolean,
    override val gender: Gender,
    override val id: Int,
    override val knownForDepartment: String,
    override val name: String,
    override val originalName: String,
    override val popularity: Double,
    override val profileImage: ProfileImage?,
    override val roles: List<Role>?,
    override val totalEpisodeCount: Int,
    override val order: Int,
) : CastBase(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profileImage = profileImage,
        roles = roles,
        totalEpisodeCount = totalEpisodeCount,
        order = order,
    )
