package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.CreatedBy

data class CreatedByDTO(
    val id: Long,
    @SerializedName("credit_id")
    val creditId: String,
    val name: String,
    val gender: Long,
    @SerializedName("profile_path")
    val profilePath: String,
){
    fun mapToDomain(): CreatedBy {
        return CreatedBy(
            id = id,
            creditId = creditId,
            name = name,
            gender = gender,
            profilePath = profilePath,
        )
    }
}
