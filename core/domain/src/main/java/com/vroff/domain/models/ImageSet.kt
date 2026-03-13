package com.vroff.domain.models

data class ImageSet(
    var verticalPoster: VerticalPoster,
    var horizontalPoster: HorizontalPoster,
    var verticalBackdrop: VerticalPoster?,
    var horizontalBackdrop: HorizontalPoster?
)