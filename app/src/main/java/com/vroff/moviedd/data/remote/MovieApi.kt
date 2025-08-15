package com.vroff.moviedd.data.remote

import com.vroff.moviedd.data.remote.entity.ShowDTO
import com.vroff.moviedd.data.remote.entity.ShowsDTO
import com.vroff.moviedd.domain.models.StreamingServices
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/shows/{id}")
    suspend fun getShow(
        @Path("id") id: String
    ): ShowDTO

    @GET("/shows/search/filters")
    suspend fun getShows(@Query("country") country: String): ShowsDTO

    @GET("/shows/top")
    suspend fun getTopShows(
        @Query("country") country: String,
        @Query("services") services: String,
        @Query("show_type") showType: String
    ): ShowsDTO

    @GET("/shows/search/title")
    suspend fun getShowByTitle(
        @Query("title") title: String,
        @Query("country") country: String
    ): List<ShowDTO>
}