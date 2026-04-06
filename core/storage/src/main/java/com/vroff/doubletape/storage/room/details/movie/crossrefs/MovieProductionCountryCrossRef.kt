package com.vroff.doubletape.storage.room.details.movie.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_country_cross_ref",
    primaryKeys = ["movieId", "iso31661"],
    indices = [Index(value = ["iso31661"])],
)
data class MovieProductionCountryCrossRef(
    val movieId: Int,
    val iso31661: String,
)
