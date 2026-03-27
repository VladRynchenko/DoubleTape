package com.vroff.domain.model.tmdb.search.typed

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.Image
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.search.KnownFor
import com.vroff.domain.model.tmdb.search.MediaType

sealed class TypedMediaItem(
    open val adult: Boolean,
    open val name: String,
    open val id: Int,
    open val image: Image?,
    open val popularity: Double,
    open val mediaType: MediaType,
)

data class MovieMediaItem(
    override val adult: Boolean,
    val backdropImage: BackdropImage?,
    override val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterImage: PosterImage?,
    val originalLanguage: String,
    val genres: List<Genre>,
    override val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Long,
    override val mediaType: MediaType = MediaType.MOVIE,
) : TypedMediaItem(adult, title, id, posterImage, popularity, mediaType)

data class PersonMediaItem(
    override val adult: Boolean,
    override val id: Int,
    override val name: String,
    val originalName: String,
    override val popularity: Double,
    val gender: Gender,
    val knownForDepartment: String,
    val profileImage: ProfileImage?,
    val knownFor: List<KnownFor>,
    override val mediaType: MediaType = MediaType.PERSON,
) : TypedMediaItem(adult, name, id, profileImage, popularity, mediaType)

data class SeriesMediaItem(
    override val adult: Boolean,
    val backdropImage: BackdropImage?,
    override val id: Int,
    override val name: String,
    val originalName: String,
    val overview: String,
    val posterImage: PosterImage?,
    val originalLanguage: String,
    val genres: List<Genre>,
    override val popularity: Double,
    val firstAirDate: String,
    val voteAverage: Float,
    val voteCount: Long,
    val originCountry: List<String>,
    override val mediaType: MediaType = MediaType.SERIES,
) : TypedMediaItem(adult, name, id, posterImage, popularity, mediaType)
