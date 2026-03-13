package com.vroff.domain.model.streaming_available

data class VerticalPoster(
    var w240: String,
    var w360: String,
    var w480: String,
    var w600: String,
    var w720: String,
)

data class HorizontalPoster(
    var w360: String,
    var w480: String,
    var w720: String,
    var w1080: String,
    var w1440: String,
)