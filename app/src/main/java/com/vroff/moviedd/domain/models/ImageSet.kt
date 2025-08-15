package com.vroff.moviedd.domain.models

import com.google.gson.annotations.SerializedName
import com.vroff.moviedd.data.remote.entity.HorizontalPosterDTO
import com.vroff.moviedd.data.remote.entity.VerticalPosterDTO

data class ImageSet(
    var verticalPoster: VerticalPoster,
    var horizontalPoster: HorizontalPoster,
    var verticalBackdrop: VerticalPoster?,
    var horizontalBackdrop: HorizontalPoster?
)