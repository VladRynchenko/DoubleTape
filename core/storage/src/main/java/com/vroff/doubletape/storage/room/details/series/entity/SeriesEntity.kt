package com.vroff.doubletape.storage.room.details.series.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.doubletape.storage.room.details.Cacheable

@Entity(tableName = "series_entity")
data class SeriesEntity(
    val adult: Boolean,
    val backdropImage: String?,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val homepage: String,
    @PrimaryKey
    val id: Int,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String?,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Float,
    val posterImage: String?,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Float,
    val voteCount: Int,
    val language: String,
    override val updatedAt: Long,
) : Cacheable

fun SeriesDetail.toEntity(language: String) =
    SeriesEntity(
        adult = adult,
        backdropImage = backdropImage?.path,
        episodeRunTime = episodeRunTime,
        firstAirDate = firstAirDate,
        homepage = homepage,
        id = id,
        inProduction = inProduction,
        languages = languages,
        lastAirDate = lastAirDate,
        name = name,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        popularity = popularity,
        posterImage = posterImage?.path,
        status = status,
        tagline = tagline,
        type = type,
        voteAverage = voteAverage,
        voteCount = voteCount,
        language = language,
        updatedAt = System.currentTimeMillis(),
    )
