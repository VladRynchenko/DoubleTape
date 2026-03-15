package com.vroff.tmdb.entity.common

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.common.ProductionCompany

data class ProductionCompanyDTO(
    val id: Long,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
) {
    fun mapToDomain(): ProductionCompany =
        ProductionCompany(
            id = id,
            logoPath = logoPath,
            name = name,
            originCountry = originCountry,
        )
}
