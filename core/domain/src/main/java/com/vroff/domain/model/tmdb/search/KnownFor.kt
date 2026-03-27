package com.vroff.domain.model.tmdb.search

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage

data class KnownFor(
    val adult: Boolean,
    val backdropImage: BackdropImage?,
    val id: Int,
    val name: String?,
    val originalName: String?,
    val overview: String,
    val posterImage: PosterImage?,
    val mediaType: String,
    val originalLanguage: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val firstAirDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val originCountry: List<String>?,
    val title: String?,
    val originalTitle: String?,
    val releaseDate: String?,
    val video: Boolean?,
)
