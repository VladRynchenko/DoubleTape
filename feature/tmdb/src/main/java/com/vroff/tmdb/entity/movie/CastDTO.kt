package com.vroff.tmdb.entity.movie

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.movie.Cast

data class CastDTO(
    val adult: Boolean,
    val gender: Long,
    val id: Long,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("cast_id")
    val castId: Long,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val order: Long,
){
    fun mapToDomain(): Cast {
        return Cast(
            adult = adult,
            gender = gender,
            id = id,
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profilePath = profilePath,
            castId = castId,
            character = character,
            creditId = creditId,
            order = order,
        )
    }
}
