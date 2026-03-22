package com.vroff.tmdb.entity.profile

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.profile.CombinedCredits
import com.vroff.domain.model.tmdb.profile.ShowCredit
import com.vroff.domain.model.tmdb.search.MediaType

data class ShowCreditDTO(
    val id: Int,
    val title: String? = null,
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Float = 0.0f,
    @SerializedName("media_type")
    val mediaType: String = "",
    val order: Int?,
    val character: String? = null,
    val job: String? = null,
    val department: String? = null,
) {
    fun mapToDomain(): ShowCredit {
        val mediaType = MediaType.entries.associateBy { it.type }[mediaType] ?: MediaType.UNKNOWN
        return ShowCredit(
            id = id,
            title = if (mediaType == MediaType.SERIES) name else title,
            posterPath = posterPath?.let { PosterImage(it) },
            releaseDate = if (mediaType == MediaType.SERIES) firstAirDate else releaseDate,
            voteAverage = voteAverage,
            mediaType = mediaType,
            character = character,
            job = job,
            department = department,
            order = order,
        )
    }
}

data class CombinedCreditsDTO(
    val cast: List<ShowCreditDTO>,
    val crew: List<ShowCreditDTO>,
    val id: Int,
) {
    fun mapToDomain(): CombinedCredits =
        CombinedCredits(
            cast = cast.map { it.mapToDomain() },
            crew = crew.map { it.mapToDomain() },
            id = id,
        )
}
