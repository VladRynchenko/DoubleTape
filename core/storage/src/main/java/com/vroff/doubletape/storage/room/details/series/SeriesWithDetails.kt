package com.vroff.doubletape.storage.room.details.series

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.vroff.domain.model.BackdropImage
import com.vroff.domain.model.PosterImage
import com.vroff.domain.model.tmdb.common.VideoData
import com.vroff.domain.model.tmdb.series.AggregateCredits
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCompanyEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.series.crossrefs.NetworkCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesCreatedByCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesGenreCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.series.entity.CreatedByEntity
import com.vroff.doubletape.storage.room.details.series.entity.LastEpisodeEntity
import com.vroff.doubletape.storage.room.details.series.entity.NetworksEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeasonEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesEntity

data class SeriesWithDetails(
    @Embedded
    val series: SeriesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = SeriesGenreCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "genreId",
            ),
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "iso6391",
        associateBy =
            Junction(
                value = SeriesSpokenLanguageCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "iso6391",
            ),
    )
    val spokenLanguages: List<SpokenLanguageEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = SeriesProductionCompanyCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "companyId",
            ),
    )
    val productionCompanies: List<ProductionCompanyEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "iso31661",
        associateBy =
            Junction(
                value = SeriesProductionCountryCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "iso31661",
            ),
    )
    val productionCountries: List<ProductionCountryEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "seriesId",
    )
    val seasons: List<SeasonEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = SeriesCreatedByCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "id",
            ),
    )
    val createdBy: List<CreatedByEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "seriesId",
    )
    val lastEpisodeToAir: LastEpisodeEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy =
            Junction(
                value = NetworkCrossRef::class,
                parentColumn = "seriesId",
                entityColumn = "id",
            ),
    )
    val networks: List<NetworksEntity>,
) {
    fun toDomain(
        aggregatedCreditsEntity: AggregateCredits? = null,
        videos: List<VideoData>? = null,
    ): SeriesDetail =
        SeriesDetail(
            adult = series.adult,
            backdropImage = series.backdropImage?.let { BackdropImage(it) },
            createdBy = createdBy.map { it.toDomain() },
            episodeRunTime = series.episodeRunTime,
            firstAirDate = series.firstAirDate,
            genres = genres.map { it.mapToDomain() },
            homepage = series.homepage,
            id = series.id,
            inProduction = series.inProduction,
            languages = spokenLanguages.map { it.toDomain().englishName },
            lastAirDate = series.lastAirDate,
            lastEpisodeToAir = lastEpisodeToAir?.toDomain(),
            name = series.name,
            networks = networks.map { it.toDomain() },
            numberOfEpisodes = series.numberOfEpisodes,
            numberOfSeasons = series.numberOfSeasons,
            originCountry = series.originCountry,
            originalLanguage = series.originalLanguage,
            originalName = series.originalName,
            overview = series.overview,
            popularity = series.popularity,
            posterImage = series.posterImage?.let { PosterImage(it) },
            productionCompanies = productionCompanies.map { it.toDomain() },
            productionCountries = productionCountries.map { it.toDomain() },
            seasons = seasons.map { it.toDomain() },
            spokenLanguages = spokenLanguages.map { it.toDomain() },
            status = series.status,
            tagline = series.tagline,
            type = series.type,
            voteAverage = series.voteAverage,
            voteCount = series.voteCount,
            aggregateCredits = aggregatedCreditsEntity,
            videos = videos,
        )
}
