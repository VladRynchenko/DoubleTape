package com.vroff.doubletape.di

import com.vroff.domain.repository.ShowRepository
import com.vroff.domain.repository.TMDBRepository
import com.vroff.domain.repository.TrendingRepository
import com.vroff.doubletape.data.repository.ShowRepositoryImpl
import com.vroff.tmdb.TMDBRepositoryImpl
import com.vroff.tmdb.TrendingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindShowsRepository(movieRepositoryImpl: ShowRepositoryImpl): ShowRepository

    @Binds
    fun bindTMDBRepository(tmdbRepositoryImpl: TMDBRepositoryImpl): TMDBRepository

    @Binds
    fun bindTrendingRepository(trendingRepositoryImpl: TrendingRepositoryImpl): TrendingRepository
}
