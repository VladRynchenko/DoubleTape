package com.vroff.domain.model.tmdb

import kotlinx.serialization.Serializable

@Serializable
data class TMDBConfiguration(
    val images: ImagesConfiguration?,
    val changeKeys: List<String>,
    val timestampSeconds: Long,
)

@Serializable
data class ImagesConfiguration(
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val stillSizes: List<String>,
)
