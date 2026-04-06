package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "series_created_by_cross_ref",
    primaryKeys = ["seriesId", "id"],
    indices = [
        Index(value = ["id"]),
    ],
)
data class SeriesCreatedByCrossRef(
    val seriesId: Int,
    val id: Int,
)
