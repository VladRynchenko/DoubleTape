package com.vroff.tmdb.entity.movie

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.movie.BelongsToCollection

data class BelongsToCollectionDTO(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
) {
    fun toDomain(): BelongsToCollection =
        BelongsToCollection(
            id = id,
            name = name,
            posterImage = posterPath?.let { PosterImage(it) },
            backdropImage = backdropPath?.let { BackdropImage(it) },
        )
}
