package com.vroff.domain.model.tmdb.search

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.typed.MovieSearchResult
import com.vroff.domain.model.tmdb.search.typed.PersonSearchResult
import com.vroff.domain.model.tmdb.search.typed.SeriesSearchResult
import com.vroff.domain.model.tmdb.search.typed.TypedSearchResult

data class SearchResult(
    val adult: Boolean = false,
    val backdropImage: BackdropImage?,
    val id: Int,
    val name: String = "",
    val originalName: String = "",
    val overview: String = "",
    val posterImage: PosterImage?,
    val mediaType: MediaType,
    val originalLanguage: String = "",
    val genres: List<Genre>,
    val popularity: Double,
    val firstAirDate: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Long = 0,
    val originCountry: List<String>,
    val gender: Gender = Gender.NOT_SET,
    val knownForDepartment: String = "",
    val profileImage: ProfileImage?,
    val knownFor: List<KnownFor>,
    val title: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val video: Boolean = false,
) {
    fun mapToTypedResult(): TypedSearchResult? =
        when (mediaType) {
            MediaType.PERSON ->
                PersonSearchResult(
                    adult = adult,
                    id = id,
                    name = name,
                    originalName = originalName,
                    popularity = popularity,
                    gender = gender,
                    knownForDepartment = knownForDepartment,
                    profileImage = profileImage,
                    knownFor = knownFor,
                )

            MediaType.MOVIE ->
                MovieSearchResult(
                    adult = adult,
                    backdropImage = backdropImage,
                    id = id,
                    title = title,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterImage = posterImage,
                    originalLanguage = originalLanguage,
                    genres = genres,
                    popularity = popularity,
                    releaseDate = releaseDate,
                    video = video,
                    voteAverage = voteAverage,
                    voteCount = voteCount,
                )

            MediaType.SERIES ->
                SeriesSearchResult(
                    adult = adult,
                    backdropImage = backdropImage,
                    id = id,
                    name = name,
                    originalName = originalName,
                    overview = overview,
                    posterImage = posterImage,
                    originalLanguage = originalLanguage,
                    genres = genres,
                    popularity = popularity,
                    firstAirDate = firstAirDate,
                    voteAverage = voteAverage,
                    voteCount = voteCount,
                    originCountry = originCountry,
                )

            else -> null
        }
}
