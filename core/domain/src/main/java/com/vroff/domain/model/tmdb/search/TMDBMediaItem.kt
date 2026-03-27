package com.vroff.domain.model.tmdb.search

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import com.vroff.domain.model.tmdb.search.typed.PersonMediaItem
import com.vroff.domain.model.tmdb.search.typed.SeriesMediaItem
import com.vroff.domain.model.tmdb.search.typed.TypedMediaItem

data class TMDBMediaItem(
    val adult: Boolean = false,
    val backdropImage: BackdropImage?,
    val id: Int,
    val name: String = "",
    val originalName: String = "",
    val overview: String = "",
    val posterImage: PosterImage?,
    val mediaType: MediaType?,
    val originalLanguage: String = "",
    val genres: List<Genre>,
    val popularity: Double,
    val firstAirDate: String = "",
    val voteAverage: Float = 0.0f,
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
    fun mapToTypedResult(selectedMediaType: MediaType? = null): TypedMediaItem? =
        when (mediaType ?: selectedMediaType) {
            MediaType.PERSON -> this.toPerson()
            MediaType.MOVIE -> this.toMovie()
            MediaType.SERIES -> this.toSeries()
            else -> null
        }

    private fun toPerson(): TypedMediaItem =
        PersonMediaItem(
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

    fun toSeries(): SeriesMediaItem =
        SeriesMediaItem(
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

    fun toMovie(): MovieMediaItem =
        MovieMediaItem(
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
}
