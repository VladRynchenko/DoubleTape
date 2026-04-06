package com.vroff.doubletape.storage.room.details.movie.entity

import androidx.room.Entity
import com.vroff.domain.model.ProfileImage
import com.vroff.domain.model.tmdb.common.Cast
import com.vroff.domain.model.tmdb.search.Gender

@Entity(
    tableName = "cast_members",
    primaryKeys = ["id", "movieId"],
)
data class CastEntity(
    val id: Int,
    val movieId: Int,
    val adult: Boolean,
    val gender: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profileImage: String?,
    val castId: Int,
    val character: String,
    val creditId: String,
    val order: Int,
) {
    fun toDomain(): Cast =
        Cast(
            adult = adult,
            gender = Gender.entries[gender],
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profileImage = profileImage?.let { ProfileImage(it) },
            castId = castId,
            character = character,
            creditId = creditId,
            order = order,
        )
}

fun Cast.toEntity(movieId: Int): CastEntity =
    CastEntity(
        id = id,
        movieId = movieId,
        adult = adult,
        gender = gender.ordinal,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profileImage = profileImage?.path,
        castId = castId,
        character = character,
        creditId = creditId,
        order = order,
    )
