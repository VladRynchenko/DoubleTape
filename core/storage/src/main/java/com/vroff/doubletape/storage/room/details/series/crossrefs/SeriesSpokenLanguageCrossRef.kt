package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "series_language_cross_ref",
    primaryKeys = ["seriesId", "iso6391"],
    indices = [
        Index(value = ["iso6391"]),
    ],
)
data class SeriesSpokenLanguageCrossRef(
    val seriesId: Int,
    val iso6391: String,
)
