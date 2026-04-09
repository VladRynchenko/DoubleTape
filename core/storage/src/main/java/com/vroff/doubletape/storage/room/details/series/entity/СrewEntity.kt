package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import com.vroff.domain.model.tmdb.series.SeriesCrew

@Entity(
    tableName = "series_crew_entity",
    primaryKeys = ["id", "seriesId"],
)
data class SeriesCrewEntity(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val seriesId: Int,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profileImage: String?,
    val department: String,
    val totalEpisodeCount: Int,
)

fun SeriesCrew.toEntity(seriesId: Int): SeriesCrewEntity =
    SeriesCrewEntity(
        adult = adult,
        gender = gender.ordinal,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profileImage = profileImage?.path,
        department = department,
        totalEpisodeCount = totalEpisodeCount,
        seriesId = seriesId,
    )
