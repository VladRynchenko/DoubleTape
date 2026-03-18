package com.vroff.domain.model.tmdb.search

import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(
    val type: String,
    val isShow: Boolean = false,
) {
    PERSON("person"),
    MOVIE("movie", true),
    SERIES("tv", true),
    UNKNOWN("unknown"),
    ;

    companion object {
    }
}
