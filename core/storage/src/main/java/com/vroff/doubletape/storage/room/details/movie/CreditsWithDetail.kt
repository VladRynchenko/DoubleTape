package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Relation
import com.vroff.domain.model.tmdb.movie.Credits
import com.vroff.doubletape.storage.room.details.movie.entity.CastEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CrewEntity

data class CreditsWithDetail(
    val id: Int,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val cast: List<CastEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val crew: List<CrewEntity>,
) {
    fun toDomain(): Credits? =
        if (cast.isEmpty() && crew.isEmpty()) {
            null
        } else {
            Credits(
                cast = cast.map { it.toDomain() },
                crew = crew.map { it.toDomain() },
            )
        }
}
