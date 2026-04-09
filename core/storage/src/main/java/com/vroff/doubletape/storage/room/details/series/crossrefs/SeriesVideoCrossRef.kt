package com.vroff.doubletape.storage.room.details.series.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "series_video_cross_ref",
    primaryKeys = ["seriesId", "videoId"],
    indices = [Index("videoId")],
)
data class SeriesVideoCrossRef(
    val seriesId: Int,
    val videoId: String,
)
