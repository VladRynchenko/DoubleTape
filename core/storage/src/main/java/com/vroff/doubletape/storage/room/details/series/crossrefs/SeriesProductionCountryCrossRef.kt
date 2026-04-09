package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "series_country_cross_ref",
    primaryKeys = ["seriesId", "iso31661"],
    indices = [Index(value = ["iso31661"])],
)
data class SeriesProductionCountryCrossRef(
    val seriesId: Int,
    val iso31661: String,
)
