package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.common.BaseDetails
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.common.ProductionCompany
import com.vroff.domain.model.tmdb.common.ProductionCountry
import com.vroff.domain.model.tmdb.common.SpokenLanguage
import com.vroff.domain.model.tmdb.common.VideoData

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
    override val videos: List<VideoData>?,
) : BaseDetails(
        adult = adult,
        homepage = homepage,
        id = id,
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        genres = genres,
        voteAverage = voteAverage,
        voteCount = voteCount,
        spokenLanguages = spokenLanguages,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        credits = credits,
        backdrop = backdrop,
        budget = budget,
        originalTitle = originalTitle,
        posterImage = posterImage,
        imdbId = imdbId,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        videos = videos,
    )
