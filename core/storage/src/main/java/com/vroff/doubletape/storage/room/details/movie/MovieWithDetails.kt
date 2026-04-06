package com.vroff.doubletape.storage.room.details.movie

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.common.VideoData
import com.vroff.domain.model.tmdb.movie.Credits
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCompanyEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieGenreCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieVideoCrossRef
import com.vroff.doubletape.storage.room.details.movie.entity.BelongsToCollectionEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CastEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CrewEntity
import com.vroff.doubletape.storage.room.details.movie.entity.MovieEntity

data class MovieWithDetails(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = MovieGenreCrossRef::class,
                parentColumn = "movieId",
                entityColumn = "genreId",
            ),
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val belongsToCollection: BelongsToCollectionEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "iso6391",
        associateBy =
            Junction(
                value = MovieSpokenLanguageCrossRef::class,
                parentColumn = "movieId",
                entityColumn = "iso6391",
            ),
    )
    val spokenLanguages: List<SpokenLanguageEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = MovieProductionCompanyCrossRef::class,
                parentColumn = "movieId",
                entityColumn = "companyId",
            ),
    )
    val productionCompanies: List<ProductionCompanyEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "iso31661",
        associateBy =
            Junction(
                value = MovieProductionCountryCrossRef::class,
                parentColumn = "movieId",
                entityColumn = "iso31661",
            ),
    )
    val productionCountries: List<ProductionCountryEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val cast: List<CastEntity>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
    )
    val crew: List<CrewEntity>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = MovieVideoCrossRef::class,
                parentColumn = "movieId",
                entityColumn = "videoId",
            ),
    )
    val videos: List<VideoEntity>?,
) {
    fun toDomain(
        credits: Credits?,
        videos: List<VideoData>?,
    ): MovieDetail =
        MovieDetail(
            adult = movie.adult,
            backdrop = movie.backdrop?.let { BackdropImage(it) },
            budget = movie.budget,
            homepage = movie.homepage,
            id = movie.id,
            imdbId = movie.imdbId,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterImage = movie.posterImage?.let { PosterImage(it) },
            productionCompanies = productionCompanies.map { it.toDomain() },
            productionCountries = productionCountries.map { it.toDomain() },
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            status = movie.status,
            tagline = movie.tagline,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            genres = genres.map { it.mapToDomain() },
            spokenLanguages = spokenLanguages.map { it.toDomain() },
            videos = videos,
            credits = credits,
            belongsToCollection = belongsToCollection?.toDomain(),
        )
}
