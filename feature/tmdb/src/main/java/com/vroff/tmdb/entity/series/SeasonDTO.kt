package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.series.Season

data class SeasonDTO(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("vote_average")
    val voteAverage: Float,
) {
    fun mapToDomain(): Season =
        Season(
            airDate = airDate,
            episodeCount = episodeCount,
            id = id,
            name = name,
            overview = overview,
            posterImage = posterPath?.let { PosterImage(it) },
            seasonNumber = seasonNumber,
            voteAverage = voteAverage,
        )
}
