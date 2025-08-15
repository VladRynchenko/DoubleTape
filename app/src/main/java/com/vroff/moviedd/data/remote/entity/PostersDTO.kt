package com.vroff.moviedd.data.remote.entity

import com.google.gson.annotations.SerializedName

data class VerticalPosterDTO(
    @SerializedName("w240") var w240: String,
    @SerializedName("w360") var w360: String,
    @SerializedName("w480") var w480: String,
    @SerializedName("w600") var w600: String,
    @SerializedName("w720") var w720: String,
)

data class HorizontalPosterDTO(
    @SerializedName("w360") var w360: String,
    @SerializedName("w480") var w480: String,
    @SerializedName("w720") var w720: String,
    @SerializedName("w1080") var w1080: String,
    @SerializedName("w1440") var w1440: String,
)