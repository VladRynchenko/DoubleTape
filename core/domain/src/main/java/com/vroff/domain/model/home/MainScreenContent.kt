package com.vroff.domain.model.home

import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import com.vroff.domain.model.tmdb.search.typed.SeriesMediaItem

data class MainScreenContent(
    val nowPlayingMovie: List<MovieMediaItem>? = null,
    val popularMovie: List<MovieMediaItem>? = null,
    val upcomingMovie: List<MovieMediaItem>? = null,
    val trendingMovie: List<MovieMediaItem>? = null,
    val trendingTv: List<SeriesMediaItem>? = null,
)
