package com.vroff.domain.model.tmdb.series

data class SeriesCast(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val roles: List<Role?>?,
    val totalEpisodeCount: Long,
    val order: Long,
)
