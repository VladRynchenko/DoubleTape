package com.vroff.doubletape.storage.room.details.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.doubletape.storage.room.details.Cacheable

@Entity(tableName = "movie_details")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop: String?,
    val budget: Long,
    val homepage: String,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterImage: String?,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int,
    val language: String,
    override val updatedAt: Long,
) : Cacheable

fun MovieDetail.toDatabase(language: String): MovieEntity =
    MovieEntity(
        id = id,
        adult = adult,
        backdrop = backdrop?.path,
        budget = budget,
        homepage = homepage,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterImage = posterImage?.path,
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        language = language,
        updatedAt = System.currentTimeMillis(),
    )
