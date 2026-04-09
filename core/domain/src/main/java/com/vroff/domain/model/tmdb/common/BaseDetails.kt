package com.vroff.domain.model.tmdb.common

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.movie.BelongsToCollection
import com.vroff.domain.model.tmdb.series.CreatedBy
import com.vroff.domain.model.tmdb.series.LastEpisodeToAir
import com.vroff.domain.model.tmdb.series.Network
import com.vroff.domain.model.tmdb.series.Season

open class BaseDetails(
    open val adult: Boolean,
    open val homepage: String,
    open val id: Int,
    open val originalLanguage: String,
    open val overview: String,
    open val popularity: Float,
    open val productionCompanies: List<ProductionCompany>,
    open val productionCountries: List<ProductionCountry>,
    open val genres: List<Genre>,
    open val voteAverage: Float,
    open val voteCount: Int,
    open val spokenLanguages: List<SpokenLanguage>,
    open val status: String,
    open val tagline: String,
    open val title: String,
    open val belongsToCollection: BelongsToCollection? = null,
    open val video: Boolean? = null,
    open val credits: BaseCredits? = null,
    open val backdropImage: BackdropImage? = null,
    open val createdBy: List<CreatedBy>? = null,
    open val episodeRunTime: List<Int>? = null,
    open val firstAirDate: String? = null,
    open val inProduction: Boolean? = null,
    open val languages: List<String>? = null,
    open val lastAirDate: String? = null,
    open val lastEpisodeToAir: LastEpisodeToAir? = null,
    open val networks: List<Network>? = null,
    open val numberOfEpisodes: Int? = null,
    open val numberOfSeasons: Int? = null,
    open val originCountry: List<String>? = null,
    open val type: String? = null,
    open val backdrop: BackdropImage? = null,
    open val budget: Long? = null,
    open val originalTitle: String? = null,
    open val posterImage: PosterImage? = null,
    open val seasons: List<Season>? = null,
    open val imdbId: String? = null,
    open val releaseDate: String? = null,
    open val revenue: Long? = null,
    open val runtime: Int? = null,
    open val videos: List<VideoData>? = null,
)
