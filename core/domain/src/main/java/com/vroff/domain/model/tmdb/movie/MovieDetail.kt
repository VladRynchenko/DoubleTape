package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.common.ProductionCompany
import com.vroff.domain.model.tmdb.common.ProductionCountry
import com.vroff.domain.model.tmdb.common.SpokenLanguage

data class MovieDetail(
    override val adult: Boolean,
    override val backdrop: BackdropImage?,
    override val budget: Long,
    override val genres: List<Genre>,
    override val homepage: String,
    override val id: Int,
    override val imdbId: String?,
    override val originalLanguage: String,
    override val originalTitle: String,
    override val overview: String,
    override val popularity: Float,
    override val posterImage: PosterImage?,
    override val productionCompanies: List<ProductionCompany>,
    override val productionCountries: List<ProductionCountry>,
    override val releaseDate: String,
    override val revenue: Long,
    override val runtime: Int,
    override val spokenLanguages: List<SpokenLanguage>,
    override val status: String,
    override val tagline: String,
    override val title: String,
    override val video: Boolean,
    override val voteAverage: Float,
    override val voteCount: Int,
    override val credits: Credits?,
) : BaseDetails(
        adult = adult,
        backdrop = backdrop,
        budget = budget,
        genres = genres,
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterImage = posterImage,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        credits = credits,
    )
