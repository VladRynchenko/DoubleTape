package com.vroff.tmdb.entity

import com.google.gson.annotations.SerializedName

data class TMDBError(
    val success: Boolean,
    @SerializedName("status_code")
    val statusCode: Long,
    @SerializedName("status_message")
    val statusMessage: String,
)
