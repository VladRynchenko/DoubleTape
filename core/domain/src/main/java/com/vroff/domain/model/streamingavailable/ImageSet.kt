package com.vroff.domain.model.streamingavailable

data class ImageSet(
    var verticalPoster: VerticalPoster,
    var horizontalPoster: HorizontalPoster,
    var verticalBackdrop: VerticalPoster?,
    var horizontalBackdrop: HorizontalPoster?,
)
