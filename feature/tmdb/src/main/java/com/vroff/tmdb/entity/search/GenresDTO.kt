package com.vroff.tmdb.entity.search

import com.google.gson.annotations.SerializedName
import com.vroff.tmdb.entity.common.GenreDTO

data class GenresDTO(
    @SerializedName("genres")
    val genres: List<GenreDTO>,
)
