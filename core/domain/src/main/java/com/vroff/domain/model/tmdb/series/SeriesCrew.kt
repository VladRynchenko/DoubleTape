package com.vroff.domain.model.tmdb.series

data class SeriesCrew (
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String?,
    val jobs: List<Job>,
    val department: String,
    val totalEpisodeCount: Long,
)
