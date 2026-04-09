package com.vroff.doubletape.storage.room.details.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.tmdb.common.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
) {
    fun mapToDomain(): Genre =
        Genre(
            id = id,
            name = name,
        )
}
