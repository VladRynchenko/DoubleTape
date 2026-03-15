package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.streaming_available.Genre
import com.vroff.domain.model.tmdb.common.ProductionCompany
import com.vroff.domain.model.tmdb.common.ProductionCountry
import com.vroff.domain.model.tmdb.common.SpokenLanguage


data class MovieDetail (
    val adult: Boolean,
    val backdrop: BackdropImage?,
    val belongsToCollection: Any?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val poster: PosterImage?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long,
    val credits: Credits?,
)