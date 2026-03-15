package com.vroff.doubletape.data.repository

import android.util.Log
import com.vroff.domain.model.streamingavailable.Show
import com.vroff.domain.model.streamingavailable.ShowType
import com.vroff.domain.model.streamingavailable.StreamingServices
import com.vroff.domain.repository.ShowRepository
import com.vroff.domain.util.Resource
import com.vroff.streamingmovie.api.StreamingAvailabilityApi
import com.vroff.streamingmovie.mapper.toShow
import java.util.Locale
import javax.inject.Inject

class ShowRepositoryImpl
    @Inject
    constructor(
        private val api: StreamingAvailabilityApi,
        private val locale: Locale,
    ) : ShowRepository {
        override suspend fun getShow(id: String): Resource<Show> =
            try {
                Resource.Success(data = api.getShow(id).toShow())
            } catch (e: Exception) {
                Resource.Error(message = e.message)
            }

        override suspend fun getShows(): Resource<List<Show>> =
            try {
                Resource.Success(api.getShows(country = locale.country).shows.map { it -> it.toShow() })
            } catch (e: Exception) {
                Resource.Error(message = e.message)
            }

        override suspend fun getTopShow(
            services: StreamingServices,
            showType: ShowType,
        ): Resource<List<Show>> =
            try {
                Resource.Success(
                    api
                        .getTopShows(
                            country = locale.country,
                            services = services.name,
                            showType = showType.name,
                        ).shows
                        .map {
                            it.toShow()
                        },
                )
            } catch (e: Exception) {
                Resource.Error(message = e.message)
            }

        override suspend fun getShowsByTitle(title: String): Resource<List<Show>> =
            try {
                Resource.Success(
                    api
                        .getShowByTitle(
                            title = title,
                            country = locale.country,
                        ).map { it.toShow() },
                )
            } catch (e: Exception) {
                Log.e("getShowsByTitle", e.message.toString())
                Resource.Error(message = e.message)
            }
    }
