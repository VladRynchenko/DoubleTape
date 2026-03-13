package com.vroff.tmdb.entity.search

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.search.SearchResult

data class SearchResultDTO(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Long,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Long>?,
    val popularity: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Long?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    val gender: Long?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("known_for")
    val knownFor: List<KnownForDTO>?,
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val video: Boolean?,
) {
    fun mapToDomain(): SearchResult {
        return SearchResult(
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
            gender = gender,
            knownForDepartment = knownForDepartment,
            profilePath = profilePath,
            knownFor = knownFor?.map { it.mapToDomain() },
            title = title,
            originalTitle = originalTitle,
            releaseDate = releaseDate,
            video = video,
        )
    }
}