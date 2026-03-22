package com.vroff.domain.model.tmdb.profile

data class CombinedCredits(
    val cast: List<ShowCredit>?,
    val crew: List<ShowCredit>?,
    val id: Int,
)
