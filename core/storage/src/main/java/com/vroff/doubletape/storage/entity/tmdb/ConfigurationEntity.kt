package com.vroff.doubletape.storage.entity.tmdb

data class ConfigurationEntity(
    val images: ImagesEntity?,
    val changeKeys: List<String>
)

data class ImagesEntity(
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val stillSizes: List<String>,
)


