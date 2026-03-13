package com.vroff.domain.model.tmdb.series


data class LastEpisodeToAir(
val id: Long,
val name: String,
val overview: String,
val voteAverage: Double,
val voteCount: Long,
val airDate: String,
val episodeNumber: Long,
val productionCode: String,
val runtime: Long,
val seasonNumber: Long,
val showId: Long,
val stillPath: String,
)
