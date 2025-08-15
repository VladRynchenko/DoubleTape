package com.vroff.moviedd.di

import com.vroff.moviedd.data.repository.ShowRepositoryImpl
import com.vroff.moviedd.domain.repository.ShowRepository
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