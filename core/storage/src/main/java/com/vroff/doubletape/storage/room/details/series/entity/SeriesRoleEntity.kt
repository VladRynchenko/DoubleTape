package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import com.vroff.domain.model.tmdb.series.Role

@Entity(
    tableName = "series_role_entity",
    primaryKeys = ["creditId", "castId"],
)
data class SeriesRoleEntity(
    val castId: Int,
    val creditId: String,
    val character: String,
    val episodeCount: Long,
) {
    fun toDomain(): Role =
        Role(
            creditId = creditId,
            character = character,
            episodeCount = episodeCount,
        )
}

fun Role.toEntity(castId: Int): SeriesRoleEntity =
    SeriesRoleEntity(
        castId = castId,
        creditId = creditId,
        character = character,
        episodeCount = episodeCount,
    )
