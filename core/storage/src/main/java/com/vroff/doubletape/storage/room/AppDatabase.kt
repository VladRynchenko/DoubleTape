package com.vroff.doubletape.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vroff.doubletape.storage.room.details.Converters
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCompanyEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.dao.CacheDao
import com.vroff.doubletape.storage.room.details.dao.SearchDao
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieGenreCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieVideoCrossRef
import com.vroff.doubletape.storage.room.details.movie.entity.BelongsToCollectionEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CastEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CrewEntity
import com.vroff.doubletape.storage.room.details.movie.entity.MovieEntity
import com.vroff.doubletape.storage.room.details.series.crossrefs.NetworkCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesCreatedByCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesGenreCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesVideoCrossRef
import com.vroff.doubletape.storage.room.details.series.entity.CreatedByEntity
import com.vroff.doubletape.storage.room.details.series.entity.LastEpisodeEntity
import com.vroff.doubletape.storage.room.details.series.entity.NetworksEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeasonEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCastEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCrewEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesJobEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesRoleEntity
import com.vroff.doubletape.storage.room.search.RecentSearchEntity

@Database(
    entities = [
        RecentSearchEntity::class,
        // Common
        GenreEntity::class,
        SpokenLanguageEntity::class,
        ProductionCountryEntity::class,
        ProductionCompanyEntity::class,
        VideoEntity::class,
        // MovieEntity
        MovieSpokenLanguageCrossRef::class,
        MovieEntity::class,
        CastEntity::class,
        CrewEntity::class,
        BelongsToCollectionEntity::class,
        MovieGenreCrossRef::class,
        MovieProductionCountryCrossRef::class,
        MovieProductionCompanyCrossRef::class,
        MovieVideoCrossRef::class,
        // SeriesEntity
        SeriesEntity::class,
        SeriesGenreCrossRef::class,
        SeriesProductionCountryCrossRef::class,
        SeriesProductionCompanyCrossRef::class,
        SeriesSpokenLanguageCrossRef::class,
        CreatedByEntity::class,
        SeriesCreatedByCrossRef::class,
        SeasonEntity::class,
        NetworkCrossRef::class,
        NetworksEntity::class,
        SeriesCastEntity::class,
        SeriesCrewEntity::class,
        SeriesVideoCrossRef::class,
        SeriesJobEntity::class,
        SeriesRoleEntity::class,
        LastEpisodeEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao

    abstract fun cacheDao(): CacheDao
}
