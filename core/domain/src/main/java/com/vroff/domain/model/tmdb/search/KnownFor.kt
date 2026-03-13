package com.vroff.domain.model.tmdb.search

data class KnownFor(
    val adult: Boolean,
    val backdropPath: String?,
    val id: Long,
    val name: String?,
    val originalName: String?,
    val overview: String,
    val posterPath: String?,
    val mediaType: String,
    val originalLanguage: String,
    val genreIds: List<Long>,
    val popularity: Double,
    val firstAirDate: String?,
    val voteAverage: Double,
    val voteCount: Long,
    val originCountry: List<String>?,
    val title: String?,
    val originalTitle: String?,
    val releaseDate: String?,
    val video: Boolean?,
)
