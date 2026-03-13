package com.vroff.doubletape.di

import com.vroff.doubletape.data.repository.ShowRepositoryImpl
import com.vroff.domain.repository.ShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindShowsRepository(movieRepositoryImpl: ShowRepositoryImpl): ShowRepository
}