package com.vroff.tmdb.di

import com.vroff.network.BuildConfig
import com.vroff.network.BuildConfig.TMDB_API_HOST
import com.vroff.network.Constants
import com.vroff.network.TMDB
import com.vroff.tmdb.api.TMDBApi
import com.vroff.tmdb.api.TrendingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object TMDBModule {
    @Provides
    @TMDB
    fun provideAuthBaseUrl(): String = TMDB_API_HOST

    @Provides
    @TMDB
    fun provideStreamingAvailabilityClient(loggerInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor { chain ->
                val request =
                    chain
                        .request()
                        .newBuilder()
                        .addHeader(
                            Constants.AUTHORIZATION,
                            Constants.BEARER + BuildConfig.TMDB_API_KEY,
                        ).build()
                chain.proceed(request)
            }.build()

    @Provides
    fun provideTMDBApi(
        @TMDB retrofit: Retrofit,
    ): TMDBApi = retrofit.create(TMDBApi::class.java)

    @Provides
    fun provideTrendingApi(
        @TMDB retrofit: Retrofit,
    ): TrendingApi =
        retrofit.create(
            TrendingApi::class.java,
        )
}
