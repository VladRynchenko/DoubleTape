package com.vroff.network

import com.vroff.network.calladapter.ResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TMDB

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StreamingAvailable

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private fun createRetrofit(
        baseUrl: String,
        client: OkHttpClient,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResourceCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    @TMDB
    fun provideTMDBRetrofit(
        @TMDB baseUrl: String,
        @TMDB client: OkHttpClient,
    ): Retrofit = createRetrofit(baseUrl, client)

    @Provides
    @Singleton
    @StreamingAvailable
    fun provideStreamingAvailabilityRetrofit(
        @StreamingAvailable baseUrl: String,
        @StreamingAvailable client: OkHttpClient,
    ): Retrofit = createRetrofit(baseUrl, client)

    @Provides
    fun provideLoggerInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
}
