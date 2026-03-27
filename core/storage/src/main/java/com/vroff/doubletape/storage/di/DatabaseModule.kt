package com.vroff.doubletape.storage.di

import android.content.Context
import androidx.room.Room
import com.vroff.domain.repository.RecentSearchRepository
import com.vroff.doubletape.storage.RecentSearchRepositoryImpl
import com.vroff.doubletape.storage.room.AppDatabase
import com.vroff.doubletape.storage.room.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "app_database",
            ).build()

    @Provides
    fun provideSearchDao(db: AppDatabase): SearchDao = db.searchDao()

    @Provides
    fun recentSearchRepository(searchDao: SearchDao): RecentSearchRepository = RecentSearchRepositoryImpl(searchDao)
}
