package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vroff.domain.model.StillImage
import com.vroff.domain.model.tmdb.series.LastEpisodeToAir

@Entity(
    tableName = "last_episode_to_air_entity",
    foreignKeys = [
        ForeignKey(
            entity = SeriesEntity::class,
            parentColumns = ["id"],
            childColumns = ["seriesId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("seriesId")],
)
data class LastEpisodeEntity(
    @PrimaryKey
    val id: Int,
    val seriesId: Int,
    val name: String,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Long,
    val airDate: String,
    val episodeNumber: Long,
    val productionCode: String,
    val runtime: Long,
    val seasonNumber: Long,
    val showId: Int,
    val stillPath: String?,
) {
    fun toDomain(): LastEpisodeToAir =
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
            stillImage = stillPath?.let { StillImage(it) },
        )
}

fun LastEpisodeToAir.toEntity(seriesId: Int): LastEpisodeEntity =
    LastEpisodeEntity(
        id = id,
        seriesId = seriesId,
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
        stillPath = stillImage?.path,
    )
