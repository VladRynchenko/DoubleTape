package com.vroff.domain.model.tmdb.series

data class Season(
    val airDate: String,
    val episodeCount: Long,
    val id: Long,
    val name: String,
    val overview: String,
    val posterPath: String,
    val seasonNumber: Long,
    val voteAverage: Double,
)
