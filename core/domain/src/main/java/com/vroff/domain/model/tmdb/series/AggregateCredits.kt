package com.vroff.domain.model.tmdb.series

data class AggregateCredits(
    val cast: List<SeriesCast?>?,
    val crew: List<SeriesCrew?>?,
)
