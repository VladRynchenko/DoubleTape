package com.vroff.doubletape.storage.room.details.series

import androidx.room.Junction
import androidx.room.Relation
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesVideoCrossRef

data class SeriesVideo(
    val id: Int,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = SeriesVideoCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "videoId",
            ),
    )
    val videos: List<VideoEntity>,
)
