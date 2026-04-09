package com.vroff.tmdb.entity.common

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.common.ProductionCountry

data class ProductionCountryDTO(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    val name: String,
) {
    fun toDomain(): ProductionCountry =
        ProductionCountry(
            iso31661 = iso31661,
            name = name,
        )
}
