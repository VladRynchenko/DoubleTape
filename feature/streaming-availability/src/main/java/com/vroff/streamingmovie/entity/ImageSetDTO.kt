package com.vroff.streamingmovie.entity

import com.google.gson.annotations.SerializedName

data class ImageSetDTO(
    @SerializedName("verticalPoster") var verticalPoster: VerticalPosterDTO,
    @SerializedName("horizontalPoster") var horizontalPoster: HorizontalPosterDTO,
    @SerializedName("verticalBackdrop") var verticalBackdrop: VerticalPosterDTO,
    @SerializedName("horizontalBackdrop") var horizontalBackdrop: HorizontalPosterDTO,
)
