package com.vroff.domain.model.tmdb.movie

import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage

data class BelongsToCollection(
    val id: Int,
    val name: String,
    val posterImage: PosterImage?,
    val backdropImage: BackdropImage?,
)
