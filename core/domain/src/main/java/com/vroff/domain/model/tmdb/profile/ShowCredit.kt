package com.vroff.domain.model.tmdb.profile

import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.search.MediaType

data class ShowCredit(
    val id: Int,
    val title: String? = null,
    val posterPath: PosterImage? = null,
    val releaseDate: String? = null,
    val voteAverage: Float = 0.0f,
    val mediaType: MediaType,
    val order: Int?,
    val character: String? = null,
    val job: String? = null,
    val department: String? = null,
)
