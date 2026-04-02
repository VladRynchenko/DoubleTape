package com.vroff.tmdb.entity.search

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.Gender
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.TMDBMediaItem
import kotlin.text.orEmpty

data class MediaItemDTO(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val popularity: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Long?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    val gender: Int,
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
    fun mapToDomain(
        genreMapper: (List<Int>?, MediaType) -> List<Genre>,
        selectedMediaType: MediaType? = null,
    ): TMDBMediaItem {
        val mediaType = selectedMediaType ?: MediaType.entries.associateBy { it.type }[mediaType] ?: MediaType.UNKNOWN
        return TMDBMediaItem(
            adult = adult,
            backdropImage = backdropPath?.let { BackdropImage(it) },
            id = id,
            name = name.orEmpty(),
            originalName = originalName.orEmpty(),
            overview = overview.orEmpty(),
            posterImage = posterPath?.let { PosterImage(it) },
            mediaType = mediaType,
            originalLanguage = originalLanguage.orEmpty(),
            genres = genreMapper(genreIds, mediaType),
            popularity = popularity,
            firstAirDate = firstAirDate.orEmpty(),
            voteAverage = voteAverage ?: 0.0f,
            voteCount = voteCount ?: 0,
            originCountry = originCountry.orEmpty(),
            gender = Gender.entries[gender],
            knownForDepartment = knownForDepartment.orEmpty(),
            profileImage = profilePath?.let { ProfileImage(it) },
            knownFor = knownFor?.map { it.mapToDomain() } ?: emptyList(),
            title = title.orEmpty(),
            originalTitle = originalTitle.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            video = video ?: false,
        )
    }
}
