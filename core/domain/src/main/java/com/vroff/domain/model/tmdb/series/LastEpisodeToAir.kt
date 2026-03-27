package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.StillImage

data class LastEpisodeToAir(
    val id: Int,
    val name: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Long,
    val airDate: String,
    val episodeNumber: Long,
    val productionCode: String,
    val runtime: Long,
    val seasonNumber: Long,
    val showId: Int,
    val stillImage: StillImage?,
)
