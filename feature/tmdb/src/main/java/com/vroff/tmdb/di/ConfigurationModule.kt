package com.vroff.tmdb.di

import com.vroff.domain.repository.ConfigManager
import com.vroff.domain.repository.GenresManager
import com.vroff.tmdb.manager.ConfigManagerImpl
import com.vroff.tmdb.manager.GenresManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConfigurationModule {
    @Binds
    fun bindConfigManager(impl: ConfigManagerImpl): ConfigManager

    @Binds
    @Singleton
    fun bindGenresManager(impl: GenresManagerImpl): GenresManager
}
