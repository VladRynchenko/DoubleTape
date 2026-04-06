package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "series_genre_cross_ref",
    primaryKeys = ["seriesId", "genreId"],
    indices = [
        Index(value = ["genreId"]),
    ],
)
data class SeriesGenreCrossRef(
    val seriesId: Int,
    val genreId: Int,
)
