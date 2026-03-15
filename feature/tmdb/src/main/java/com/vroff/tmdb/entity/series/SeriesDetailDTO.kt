package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.tmdb.entity.common.GenreDTO
import com.vroff.tmdb.entity.common.ProductionCompanyDTO
import com.vroff.tmdb.entity.common.ProductionCountryDTO
import com.vroff.tmdb.entity.common.SpokenLanguageDTO

data class SeriesDetailDTO(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: List<CreatedByDTO>,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Long>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    val genres: List<GenreDTO>,
    val homepage: String,
    val id: Long,
    @SerializedName("in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirDTO,
    val name: String,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Any?,
    val networks: List<NetworkDTO>,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Long,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Long,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDTO>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDTO>,
    val seasons: List<SeasonDTO>,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDTO>,
    val status: String,
    val tagline: String,
    val type: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    @SerializedName("aggregate_credits")
    val aggregateCredits: AggregateCreditsDTO,
) {
    fun mapToDomain(): SeriesDetail {
        return SeriesDetail(
            adult = adult,
            backdropImage = backdropPath?.let { BackdropImage(it) },
            createdBy = createdBy.map { it.mapToDomain() },
            episodeRunTime = episodeRunTime,
            firstAirDate = firstAirDate,
            genres = genres.map { it.mapToDomain() },
            homepage = homepage,
            id = id,
            inProduction = inProduction,
            languages = languages,
            lastAirDate = lastAirDate,
            lastEpisodeToAir = lastEpisodeToAir.mapToDomain(),
            name = name,
            nextEpisodeToAir = nextEpisodeToAir,
            networks = networks.map { it.mapToDomain() },
            numberOfEpisodes = numberOfEpisodes,
            numberOfSeasons = numberOfSeasons,
            originCountry = originCountry,
            originalLanguage = originalLanguage,
            originalName = originalName,
            overview = overview,
            popularity = popularity,
            posterImage = posterPath?.let { PosterImage(it) },
            productionCompanies = productionCompanies.map { it.mapToDomain() },
            productionCountries = productionCountries.map { it.mapToDomain() },
            seasons = seasons.map { it.mapToDomain() },
            spokenLanguages = spokenLanguages.map { it.mapToDomain() },
            status = status,
            tagline = tagline,
            type = type,
            voteAverage = voteAverage,
            voteCount = voteCount,
            aggregateCredits = aggregateCredits.mapToDomain(),
        )
    }
}