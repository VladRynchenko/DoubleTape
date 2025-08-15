package com.vroff.moviedd.data.remote.entity

import com.google.gson.annotations.SerializedName

data class ShowsDTO(
    @SerializedName("shows") val shows: ArrayList<ShowDTO>
)

