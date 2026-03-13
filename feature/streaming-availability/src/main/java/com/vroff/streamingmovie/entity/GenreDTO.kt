package com.vroff.streamingmovie.entity

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
)