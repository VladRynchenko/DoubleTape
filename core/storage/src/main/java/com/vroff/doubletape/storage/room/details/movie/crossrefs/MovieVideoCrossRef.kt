package com.vroff.doubletape.storage.room.details.movie.crossrefs

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "movie_video_cross_ref",
    primaryKeys = ["movieId", "videoId"],
    indices = [Index("videoId")],
)
data class MovieVideoCrossRef(
    val movieId: Int,
    val videoId: String,
)
