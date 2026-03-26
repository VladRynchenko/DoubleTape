package com.vroff.tmdb

object Endpoint {
    private const val MOVIE = "movie"
    private const val SERIES = "tv"
    private const val GENRE = "genre"
    private const val PROFILE = "person"
    const val MOVIE_GENRES: String = "$GENRE/$MOVIE/list"
    const val SERIES_GENRES: String = "$GENRE/$SERIES/list"
    const val CONFIGURATION = "configuration"
    private const val SEARCH = "search"
    const val MULTI_SEARCH = "$SEARCH/multi"
    const val MOVIE_DETAILS = "$MOVIE/{movie_id}"
    const val CREDITS = "credits"
    const val SERIES_DETAILS = "$SERIES/{series_id}"
    const val AGGREGATE_CREDITS = "aggregate_credits"

    const val PROFILE_DETAILS = "$PROFILE/{profile_id}"

    // Trending
    private const val TRENDING = "trending"
    const val TRENDING_ALL = "$TRENDING/all/{time_window}"
    const val TRENDING_MOVIE = "$TRENDING/$MOVIE/{time_window}"
    const val TRENDING_TV = "$TRENDING/$SERIES/{time_window}"

    const val POPULAR_TV = "$SERIES/popular"
    const val AIRING_TODAY_TV = "$SERIES/airing_today"
    const val ON_THE_AIR_TV = "$SERIES/on_the_air"
    const val TOP_RATED_TV = "$SERIES/top_rated"

    const val POPULAR_MOVIE = "$MOVIE/popular"
    const val NOW_PLAYING_MOVIE = "$MOVIE/now_playing"
    const val TOP_RATED_MOVIE = "$MOVIE/top_rated"
    const val UPCOMING_MOVIE = "$MOVIE/upcoming"
}
