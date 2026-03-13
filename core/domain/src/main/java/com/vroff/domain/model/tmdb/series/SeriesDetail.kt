package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.common.ProductionCompany
import com.vroff.domain.model.tmdb.common.ProductionCountry
import com.vroff.domain.model.tmdb.common.SpokenLanguage

data class SeriesDetail(
    val adult: Boolean,
    val backdropPath: String,
    val createdBy: List<CreatedBy>,
    val episodeRunTime: List<Long>,
    val firstAirDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String,
    val lastEpisodeToAir: LastEpisodeToAir,
    val name: String,
    val nextEpisodeToAir: Any?,
    val networks: List<Network>,
    val numberOfEpisodes: Long,
    val numberOfSeasons: Long,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Long,
    val aggregateCredits: AggregateCredits,
)