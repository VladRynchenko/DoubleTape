package com.vroff.moviedd.di

import android.content.Context
import android.location.Location
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.svg.SvgDecoder
import com.vroff.moviedd.BuildConfig
import com.vroff.moviedd.data.remote.MovieApi
import com.vroff.moviedd.domain.util.Constants
import com.vroff.moviedd.domain.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideShowsApi(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    fun providesClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(Constants.X_RAPIDAPI_KEY, BuildConfig.API_KEY)
                    .addHeader(Constants.X_RAPIDAPI_HOST, BuildConfig.API_HOST)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton // Делаем ImageLoader синглтоном
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory()) // Добавляем SvgDecoder
            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache_hilt"))
                    .maxSizeBytes(5L * 1024 * 1024)
                    .build()
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideLocale(): Locale {
        return Locale.getDefault()
    }
}