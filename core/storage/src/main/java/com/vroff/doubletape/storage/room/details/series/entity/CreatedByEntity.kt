package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.series.CreatedBy

@Entity(
    tableName = "created_by",
    primaryKeys = ["id", "seriesId"],
)
data class CreatedByEntity(
    val id: Int,
    val seriesId: Int,
    val creditId: String,
    val name: String,
    val gender: Int,
    val profileImage: String?,
) {
    fun toDomain(): CreatedBy =
        CreatedBy(
            id = id,
            creditId = creditId,
            name = name,
            gender = gender,
            profileImage = profileImage?.let { ProfileImage(it) },
        )
}

fun CreatedBy.toEntity(seriesId: Int): CreatedByEntity =
    CreatedByEntity(
        seriesId = seriesId,
        id = id,
        creditId = creditId,
        name = name,
        gender = gender,
        profileImage = profileImage?.path,
    )
