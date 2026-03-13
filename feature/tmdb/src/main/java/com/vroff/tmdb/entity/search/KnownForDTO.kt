package com.vroff.tmdb.entity.search

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.search.KnownFor

data class KnownForDTO(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Long,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("genre_ids")
    val genreIds: List<Long>,
    val popularity: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val video: Boolean?,
) {
    fun mapToDomain(): KnownFor {
        return KnownFor(
            adult = adult,
            backdropPath = backdropPath,
            id = id,
            name = name,
            originalName = originalName,
            overview = overview,
            posterPath = posterPath,
            mediaType = mediaType,
            originalLanguage = originalLanguage,
            genreIds = genreIds,
            popularity = popularity,
            firstAirDate = firstAirDate,
            voteAverage = voteAverage,
            voteCount = voteCount,
            originCountry = originCountry,
            title = title,
            originalTitle = originalTitle,
            releaseDate = releaseDate,
            video = video,
        )
    }

}
