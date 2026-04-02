package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_language_cross_ref",
    primaryKeys = ["movieId", "iso6391"],
    indices = [
        Index(value = ["iso6391"]),
    ],
)
data class MovieSpokenLanguageCrossRef(
    val movieId: Int,
    val iso6391: String,
)
