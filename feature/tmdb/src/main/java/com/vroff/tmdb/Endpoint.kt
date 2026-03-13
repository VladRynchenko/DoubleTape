package com.vroff.tmdb

object Endpoint {
    const val SEARCH = "search"
    const val MULTI_SEARCH = "$SEARCH/multi"
    const val MOVIE_DETAILS = "movie/{movie_id}"
    const val CREDITS = "credits"
    const val SERIES_DETAILS = "tv/{series_id}"
    const val AGGREGATE_CREDITS = "aggregate_credits"

}