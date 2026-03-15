package com.vroff.tmdb.entity

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.ImagesConfiguration
import com.vroff.domain.model.tmdb.TMDBConfiguration

data class Configuration(
    val images: Images? = null,
    @SerializedName("changed_keys")
    val changeKeys: List<String> = emptyList(),
) {
    fun mapToDomain() =
        TMDBConfiguration(
            changeKeys = changeKeys,
            images = images?.mapToDomain(),
            timestampSeconds = System.currentTimeMillis() / 1000,
        )
}

data class Images(
    @SerializedName("base_url")
    val baseUrl: String,
    @SerializedName("secure_base_url")
    val secureBaseUrl: String,
    @SerializedName("backdrop_sizes")
    val backdropSizes: List<String>,
    @SerializedName("logo_sizes")
    val logoSizes: List<String>,
    @SerializedName("poster_sizes")
    val posterSizes: List<String>,
    @SerializedName("profile_sizes")
    val profileSizes: List<String>,
    @SerializedName("still_sizes")
    val stillSizes: List<String>,
) {
    fun mapToDomain() =
        ImagesConfiguration(
            baseUrl = baseUrl,
            secureBaseUrl = secureBaseUrl,
            backdropSizes = backdropSizes,
            logoSizes = logoSizes,
            posterSizes = posterSizes,
            profileSizes = profileSizes,
            stillSizes = stillSizes,
        )
}
