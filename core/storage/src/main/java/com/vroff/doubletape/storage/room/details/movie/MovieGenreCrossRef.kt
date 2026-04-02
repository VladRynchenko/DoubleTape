package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_genre_cross_ref",
    primaryKeys = ["movieId", "genreId"],
    indices = [
        Index(value = ["genreId"]),
    ],
)
data class MovieGenreCrossRef(
    val movieId: Int,
    val genreId: Int,
)
