package com.vroff.tmdb.entity.common

import com.vroff.domain.model.tmdb.common.Genre

data class GenreDTO(
    val id: Long,
    val name: String,
) {
    fun mapToDomain(): Genre =
        Genre(
            id = id,
            name = name,
        )
}
