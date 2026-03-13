package com.vroff.domain.model.streaming_available

data class Season(
    var title: String,
    var firstAirYear: Int,
    var lastAirYear: Int,
    var episodes: List<Episode>
)
