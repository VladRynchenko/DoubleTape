package com.vroff.domain.model.tmdb.search.typed

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.search.KnownFor

sealed class TypedSearchResult(
    open val adult: Boolean,
    open val id: Int,
    open val popularity: Double,
)

data class MovieSearchResult(
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
    val voteAverage: Double,
    val voteCount: Long,
) : TypedSearchResult(adult, id, popularity)

data class PersonSearchResult(
    override val adult: Boolean,
    override val id: Int,
    val name: String,
    val originalName: String,
    override val popularity: Double,
    val gender: Gender,
    val knownForDepartment: String,
    val profileImage: ProfileImage?,
    val knownFor: List<KnownFor>,
) : TypedSearchResult(adult, id, popularity)

data class SerialSearchResult(
    override val adult: Boolean,
    val backdropImage: BackdropImage?,
    override val id: Int,
    val name: String,
    val originalName: String,
    val overview: String,
    val posterImage: PosterImage?,
    val originalLanguage: String,
    val genres: List<Genre>,
    override val popularity: Double,
    val firstAirDate: String,
    val voteAverage: Double,
    val voteCount: Long,
    val originCountry: List<String>,
) : TypedSearchResult(adult, id, popularity)
