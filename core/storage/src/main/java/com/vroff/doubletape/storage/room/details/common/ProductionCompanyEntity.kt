package com.vroff.doubletape.storage.room.details.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.LogoImage
import com.vroff.domain.model.tmdb.common.ProductionCompany

@Entity(tableName = "production_companies")
data class ProductionCompanyEntity(
    @PrimaryKey
    val id: Int,
    val logoImage: String?,
    val name: String,
    val originCountry: String,
) {
    fun toDomain(): ProductionCompany =
        ProductionCompany(
            id = id,
            logoImage = logoImage?.let { LogoImage(it) },
            name = name,
            originCountry = originCountry,
        )
}

fun ProductionCompany.toEntity(): ProductionCompanyEntity =
    ProductionCompanyEntity(
        id = id,
        logoImage = logoImage?.path,
        name = name,
        originCountry = originCountry,
    )
