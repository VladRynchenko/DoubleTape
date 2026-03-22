package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.tmdb.common.AppendableResponse

enum class MovieAppendedToResponse(
    override val value: String,
) : AppendableResponse {
    CREDITS("credits"),
}
