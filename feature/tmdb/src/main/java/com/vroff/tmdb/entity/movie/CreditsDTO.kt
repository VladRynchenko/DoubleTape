package com.vroff.tmdb.entity.movie

import com.vroff.domain.model.tmdb.movie.Credits

data class CreditsDTO(
    val cast: List<CastDTO>,
    val crew: List<CrewDTO>,
) {
    fun mapToDomain(): Credits =
        Credits(
            cast = cast.map { it.mapToDomain() },
            crew = crew.map { it.mapToDomain() },
        )

    fun mapToDomainLight(): Credits =
        Credits(
            cast = cast.take(8).map { it.mapToDomain() },
            crew = emptyList(),
        )
}
