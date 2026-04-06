package com.vroff.doubletape.storage.room.details.movie.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.movie.BelongsToCollection

@Entity(
    tableName = "belongs_to_collection",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("movieId")],
)
data class BelongsToCollectionEntity(
    val movieId: Int,
    @PrimaryKey
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?,
) {
    fun toDomain(): BelongsToCollection =
        BelongsToCollection(
            id = id,
            name = name,
            posterImage = posterPath?.let { PosterImage(it) },
            backdropImage = backdropPath?.let { BackdropImage(it) },
        )
}

fun BelongsToCollection.toEntity(movieId: Int) =
    BelongsToCollectionEntity(
        id = id,
        movieId = movieId,
        name = name,
        posterPath = posterImage?.path,
        backdropPath = backdropImage?.path,
    )
