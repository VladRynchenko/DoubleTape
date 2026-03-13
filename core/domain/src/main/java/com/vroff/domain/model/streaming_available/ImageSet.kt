package com.vroff.domain.model.streaming_available

data class ImageSet(
    var verticalPoster: VerticalPoster,
    var horizontalPoster: HorizontalPoster,
    var verticalBackdrop: VerticalPoster?,
    var horizontalBackdrop: HorizontalPoster?
)