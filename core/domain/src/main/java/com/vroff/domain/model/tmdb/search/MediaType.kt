package com.vroff.domain.model.tmdb.search

import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(val type: String) {
    PERSON("person"),
    MOVIE("movie"),
    SERIES("tv"),
    UNKNOWN("unknown")
}