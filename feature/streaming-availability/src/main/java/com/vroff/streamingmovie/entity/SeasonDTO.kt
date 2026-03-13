package com.vroff.streamingmovie.entity

import com.google.gson.annotations.SerializedName

data class SeasonDTO(
    @SerializedName("title") var title: String,
    @SerializedName("firstAirYear") var firstAirYear: Int,
    @SerializedName("lastAirYear") var lastAirYear: Int,
    @SerializedName("episodes") var episodes: ArrayList<EpisodeDTO>
)
