package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.tmdb.common.BaseCredits
import com.vroff.domain.model.tmdb.common.Cast

data class Credits(
    override val cast: List<Cast>,
    override val crew: List<Crew>,
) : BaseCredits(
        cast = cast,
        crew = crew,
    ) {
    fun getKeyCreators(): List<String> =
        crew
            .filter { member ->
                member.job in listOf("Director", "Writer", "Screenplay", "Original Story")
            }.map { it.name }
            .distinct()
}
