package com.vroff.tmdb.di

import com.vroff.network.BuildConfig
import com.vroff.network.BuildConfig.TMDB_API_HOST
import com.vroff.network.Constants
import com.vroff.network.TMDB
import com.vroff.tmdb.api.TMDBApi
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
    fun provideStreamingAvailabilityClient(
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        Constants.AUTHORIZATION,
                        Constants.BEARER + BuildConfig.TMDB_API_KEY
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
    }


    @Provides
    fun provideStreamingAvailability(@TMDB retrofit: Retrofit): TMDBApi {
        return retrofit.create(TMDBApi::class.java)
    }

}
