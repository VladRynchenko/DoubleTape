package com.vroff.tmdb.entity.common

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.LogoImage
import com.vroff.domain.model.tmdb.common.ProductionCompany

data class ProductionCompanyDTO(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
) {
    fun toDomain(): ProductionCompany =
        ProductionCompany(
            id = id,
            logoImage = logoPath?.let { LogoImage(it) },
            name = name,
            originCountry = originCountry,
        )
}
