package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Junction
import androidx.room.Relation
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieVideoCrossRef

data class MovieVideo(
    val id: Int,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = MovieVideoCrossRef::class,
                parentColumn = "movieId",
                entityColumn = "videoId",
            ),
    )
    val videos: List<VideoEntity>?,
)
