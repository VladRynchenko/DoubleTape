package com.vroff.tmdb.entity.common

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.Job

data class JobDTO(
    @SerializedName("credit_id")
    val creditId: String,
    val job: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
) {
    fun mapToDomain(): Job =
        Job(
            creditId = creditId,
            job = job,
            episodeCount = episodeCount,
        )
}
