package com.vroff.streamingmovie.di

import com.vroff.network.BuildConfig
import com.vroff.network.BuildConfig.STREAMING_AVAILABILITY_BASE_URL
import com.vroff.network.Constants
import com.vroff.network.StreamingAvailable
import com.vroff.streamingmovie.api.StreamingAvailabilityApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object StreamingAvailabilityModule {

    @Provides
    @StreamingAvailable
    fun provideAuthBaseUrl(): String = STREAMING_AVAILABILITY_BASE_URL

    @Provides
    @StreamingAvailable
    fun provideStreamingAvailabilityClient(
        loggerInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        Constants.X_RAPIDAPI_KEY,
                        BuildConfig.STREAMING_AVAILABILITY_API_KEY
                    )
                    .addHeader(
                        Constants.X_RAPIDAPI_HOST,
                        BuildConfig.STREAMING_AVAILABILITY_API_HOST
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideStreamingAvailability(@StreamingAvailable retrofit: Retrofit): StreamingAvailabilityApi {
        return retrofit.create(StreamingAvailabilityApi::class.java)
    }
}