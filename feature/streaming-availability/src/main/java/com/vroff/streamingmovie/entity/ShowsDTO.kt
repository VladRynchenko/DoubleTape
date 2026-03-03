package com.vroff.streamingmovie.entity

import com.google.gson.annotations.SerializedName

data class ShowsDTO(
    @SerializedName("shows") val shows: ArrayList<ShowDTO>
)

