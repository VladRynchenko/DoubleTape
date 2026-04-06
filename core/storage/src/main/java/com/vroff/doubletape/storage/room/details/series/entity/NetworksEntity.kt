package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import com.vroff.domain.model.LogoImage
import com.vroff.domain.model.tmdb.series.Network

@Entity(
    tableName = "networks",
    primaryKeys = ["id", "seriesId"],
)
data class NetworksEntity(
    val seriesId: Int,
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String,
) {
    fun toDomain(): Network =
        Network(
            id = id,
            name = name,
            logoImage = logoPath?.let { LogoImage(it) },
            originCountry = originCountry,
        )
}

fun Network.toEntity(seriesId: Int): NetworksEntity =
    NetworksEntity(
        seriesId = seriesId,
        id = id,
        name = name,
        logoPath = logoImage?.path,
        originCountry = originCountry,
    )
