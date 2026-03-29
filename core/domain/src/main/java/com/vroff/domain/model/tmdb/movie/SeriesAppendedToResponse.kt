package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.tmdb.common.AppendableResponse

enum class SeriesAppendedToResponse(
    override val value: String,
) : AppendableResponse {
    AGGREGATE_CREDITS("aggregate_credits"),
    VIDEOS("videos"),
}
