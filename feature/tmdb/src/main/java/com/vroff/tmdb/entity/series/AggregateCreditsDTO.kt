package com.vroff.tmdb.entity.series

import com.vroff.domain.model.tmdb.series.AggregateCredits

data class AggregateCreditsDTO(
    val cast: List<SeriesCastDTO>,
    val crew: List<SeriesCrewDTO>,
) {
    fun mapToDomain(): AggregateCredits =
        AggregateCredits(
            cast = cast.map { it.mapToDomain() },
            crew = crew.map { it.mapToDomain() },
        )
}
