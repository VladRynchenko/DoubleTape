package com.vroff.tmdb.entity.movie

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.streaming_available.Genre
import com.vroff.tmdb.entity.series.AggregateCreditsDTO
import com.vroff.tmdb.entity.common.ProductionCompanyDTO
import com.vroff.tmdb.entity.common.ProductionCountryDTO
import com.vroff.tmdb.entity.common.SpokenLanguageDTO

data class MovieDetailDTO(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDTO>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDTO>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDTO>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    @SerializedName("credits")
    val credits: CreditsDTO?,
) {
    fun mapToDomain(): MovieDetail {
        return MovieDetail(
            adult = adult,
            backdropPath = backdropPath,
            belongsToCollection = belongsToCollection,
            budget = budget,
            genres = genres,
            homepage = homepage,
            id = id,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            productionCompanies = productionCompanies.map { it.mapToDomain() },
            productionCountries = productionCountries.map { it.mapToDomain() },
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            spokenLanguages = spokenLanguages.map { it.mapToDomain() },
            status = status,
            tagline = tagline,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            credits = credits?.mapToDomainLight(),
        )
    }
}