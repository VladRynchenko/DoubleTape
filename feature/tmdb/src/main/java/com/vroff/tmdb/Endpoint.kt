package com.vroff.tmdb

object Endpoint {
    const val MOVIE = "movie"
    const val SERIES = "tv"
    const val GENRE = "genre"
    const val MOVIE_GENRES: String = "$GENRE/$MOVIE/list"
    const val SERIES_GENRES: String = "$GENRE/$SERIES/list"
    const val CONFIGURATION = "configuration"
    const val SEARCH = "search"
    const val MULTI_SEARCH = "$SEARCH/multi"
    const val MOVIE_DETAILS = "$MOVIE/{movie_id}"
    const val CREDITS = "credits"
    const val SERIES_DETAILS = "$SERIES/{series_id}"
    const val AGGREGATE_CREDITS = "aggregate_credits"
}
