package com.vroff.doubletape.storage.room.details.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.tmdb.common.ProductionCountry

@Entity(tableName = "production_countries")
data class ProductionCountryEntity(
    @PrimaryKey
    val iso31661: String,
    val name: String,
) {
    fun toDomain(): ProductionCountry =
        ProductionCountry(
            iso31661 = iso31661,
            name = name,
        )
}

fun ProductionCountry.toEntity(): ProductionCountryEntity =
    ProductionCountryEntity(
        iso31661 = iso31661,
        name = name,
    )
