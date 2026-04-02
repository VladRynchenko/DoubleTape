package com.vroff.doubletape.storage.room.details.common

import androidx.room.Entity
import androidx.room.Index
import com.vroff.domain.model.tmdb.common.VideoData

@Entity(
    tableName = "videos",
    primaryKeys = [
        "id",
        "movieId",
    ],
    indices = [Index(value = ["movieId"])],
)
data class VideoEntity(
    val id: String,
    val movieId: Int,
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
) {
    fun toDomain(): VideoData =
        VideoData(
            id = id,
            iso6391 = iso6391,
            iso31661 = iso31661,
            name = name,
            key = key,
            site = site,
            size = size,
            type = type,
            official = official,
            publishedAt = publishedAt,
        )
}
