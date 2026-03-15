package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.LastEpisodeToAir

data class LastEpisodeToAirDTO(
    val id: Long,
    val name: String,
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_number")
    val episodeNumber: Long,
    @SerializedName("production_code")
    val productionCode: String,
    val runtime: Long,
    @SerializedName("season_number")
    val seasonNumber: Long,
    @SerializedName("show_id")
    val showId: Long,
    @SerializedName("still_path")
    val stillPath: String,
) {
    fun mapToDomain(): LastEpisodeToAir =
        LastEpisodeToAir(
            id = id,
            name = name,
            overview = overview,
            voteAverage = voteAverage,
            voteCount = voteCount,
            airDate = airDate,
            episodeNumber = episodeNumber,
            productionCode = productionCode,
            runtime = runtime,
            seasonNumber = seasonNumber,
            showId = showId,
            stillPath = stillPath,
        )
}
