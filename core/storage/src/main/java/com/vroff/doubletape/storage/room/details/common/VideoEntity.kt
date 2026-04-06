package com.vroff.doubletape.storage.room.details.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.tmdb.common.VideoData

@Entity(
    tableName = "videos",
)
data class VideoEntity(
    @PrimaryKey
    val id: String,
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

fun List<VideoData>.toEntity(): List<VideoEntity> =
    map {
        VideoEntity(
            id = it.id,
            iso6391 = it.iso6391,
            iso31661 = it.iso31661,
            name = it.name,
            key = it.key,
            site = it.site,
            size = it.size,
            type = it.type,
            official = it.official,
            publishedAt = it.publishedAt,
        )
    }
