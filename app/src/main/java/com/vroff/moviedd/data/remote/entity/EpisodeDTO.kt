package com.vroff.moviedd.data.remote.entity

import com.google.gson.annotations.SerializedName

data class EpisodeDTO(
    @SerializedName("title") var title: String,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("airYear") var airYear: Int,
)
