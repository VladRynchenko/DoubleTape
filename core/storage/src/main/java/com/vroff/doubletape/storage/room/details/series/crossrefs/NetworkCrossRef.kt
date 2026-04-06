package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "networks_cross_ref",
    primaryKeys = ["id", "seriesId"],
    indices = [Index("seriesId")],
)
data class NetworkCrossRef(
    val seriesId: Int,
    val id: Int,
)
