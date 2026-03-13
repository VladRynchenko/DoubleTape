package com.vroff.tmdb.entity.common

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.Role

data class RoleDTO(
    @SerializedName("credit_id")
    val creditId: String,
    val character: String,
    @SerializedName("episode_count")
    val episodeCount: Long,
) {
    fun mapToDomain(): Role {
        return Role(
            creditId = creditId,
            character = character,
            episodeCount = episodeCount,
        )
    }
}
