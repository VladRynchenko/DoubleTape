package com.vroff.tmdb.di

import com.vroff.domain.repository.ConfigManager
import com.vroff.tmdb.manager.ConfigManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ConfigurationModule {

    @Binds
    fun bindConfigManager(impl: ConfigManagerImpl): ConfigManager
}
