package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.Season

data class SeasonDTO(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_count")
    val episodeCount: Long,
    val id: Long,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Long,
    @SerializedName("vote_average")
    val voteAverage: Double,
) {
    fun mapToDomain(): Season =
        Season(
            airDate = airDate,
            episodeCount = episodeCount,
            id = id,
            name = name,
            overview = overview,
            posterPath = posterPath,
            seasonNumber = seasonNumber,
            voteAverage = voteAverage,
        )
}
