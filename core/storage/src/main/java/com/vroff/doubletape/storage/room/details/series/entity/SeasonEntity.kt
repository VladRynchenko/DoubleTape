package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.series.Season

@Entity(
    tableName = "season",
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
data class SeasonEntity(
    @PrimaryKey val id: Int,
    val seriesId: Int,
    val seasonNumber: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val airDate: String?,
    val episodeCount: Int,
    val voteAverage: Float,
) {
    fun toDomain(): Season =
        Season(
            id = id,
            seasonNumber = seasonNumber,
            name = name,
            overview = overview,
            posterImage = posterPath?.let { PosterImage(it) },
            airDate = airDate,
            episodeCount = episodeCount,
            voteAverage = voteAverage,
        )
}

fun Season.toEntity(seriesId: Int): SeasonEntity =
    SeasonEntity(
        seriesId = seriesId,
        seasonNumber = seasonNumber,
        name = name,
        overview = overview,
        posterPath = posterImage?.path,
        airDate = airDate,
        episodeCount = episodeCount,
        voteAverage = voteAverage,
        id = id,
    )
