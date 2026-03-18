package com.vroff.tmdb.entity.series

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.series.Network

data class NetworkDTO(
    val id: Long,
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
) {
    fun mapToDomain(): Network =
        Network(
            id = id,
            logoImage = logoPath,
            name = name,
            originCountry = originCountry,
        )
}
