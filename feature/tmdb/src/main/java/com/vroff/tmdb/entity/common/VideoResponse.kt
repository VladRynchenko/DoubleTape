package com.vroff.tmdb.entity.common

import com.vroff.domain.model.tmdb.common.VideoData

data class VideoResponse(
    val id: Int,
    val results: List<VideoDTO>,
) {
    fun mapToDomain(): List<VideoData> =
        results.map {
            VideoData(
                iso6391 = it.iso6391,
                iso31661 = it.iso31661,
                name = it.name,
                key = it.key,
                site = it.site,
                size = it.size,
                type = it.type,
                official = it.official,
                publishedAt = it.publishedAt,
                id = it.id,
            )
        }
}
