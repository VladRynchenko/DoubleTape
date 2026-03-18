package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.tmdb.common.BaseCredits
import com.vroff.domain.model.tmdb.common.SeriesCast

data class AggregateCredits(
    override val cast: List<SeriesCast>,
    override val crew: List<SeriesCrew>,
) : BaseCredits(
        cast = cast,
        crew = crew,
    )
