package com.vroff.tmdb.entity.common

import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.util.capitalizeFirst

data class GenreDTO(
    val id: Int,
    val name: String,
) {
    fun toDomain(): Genre =
        Genre(
            id = id,
            name = name.capitalizeFirst(),
        )
}
