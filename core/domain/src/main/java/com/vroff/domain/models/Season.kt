package com.vroff.domain.models

data class Season(
    var title: String,
    var firstAirYear: Int,
    var lastAirYear: Int,
    var episodes: List<Episode>
)
