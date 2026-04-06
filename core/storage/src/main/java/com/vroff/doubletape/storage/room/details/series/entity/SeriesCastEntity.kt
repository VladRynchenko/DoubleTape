package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Relation
import com.vroff.domain.model.tmdb.common.SeriesCast

data class SeriesCastWithRoles(
    @Embedded val cast: SeriesCastEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "castId",
    )
    val roles: List<SeriesRoleEntity>,
)

@Entity(tableName = "series_cast_entity", primaryKeys = ["id", "seriesId"], indices = [Index("seriesId")])
data class SeriesCastEntity(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val seriesId: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profileImage: String?,
    val totalEpisodeCount: Int,
    val order: Int,
)

fun SeriesCast.toEntity(seriesId: Int): SeriesCastEntity =
    SeriesCastEntity(
        adult = adult,
        gender = gender.ordinal,
        id = id,
        seriesId = seriesId,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profileImage = profileImage?.path,
        totalEpisodeCount = totalEpisodeCount,
        order = order,
    )
