package com.vroff.doubletape.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vroff.doubletape.storage.entity.RecentSearchEntity
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCompanyEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.movie.CastEntity
import com.vroff.doubletape.storage.room.details.movie.CrewEntity
import com.vroff.doubletape.storage.room.details.movie.MovieEntity
import com.vroff.doubletape.storage.room.details.movie.MovieGenreCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieSpokenLanguageCrossRef

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
        MovieGenreCrossRef::class,
        MovieProductionCountryCrossRef::class,
        MovieProductionCompanyCrossRef::class,
        CastEntity::class,
        CrewEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao

    abstract fun cacheDao(): CacheDao
}
