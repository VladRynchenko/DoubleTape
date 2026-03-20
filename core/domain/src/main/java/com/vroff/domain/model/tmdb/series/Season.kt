package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.PosterImage

data class Season(
    val airDate: String?,
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val posterImage: PosterImage?,
    val seasonNumber: Int,
    val voteAverage: Float,
)
