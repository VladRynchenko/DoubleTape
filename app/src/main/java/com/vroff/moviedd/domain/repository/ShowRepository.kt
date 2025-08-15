package com.vroff.moviedd.domain.repository

import com.vroff.moviedd.domain.models.Show
import com.vroff.moviedd.domain.models.ShowType
import com.vroff.moviedd.domain.models.StreamingServices
import com.vroff.moviedd.domain.util.Resource

interface ShowRepository {

    suspend fun getShow(id: String): Resource<Show>
    suspend fun getShows(): Resource<List<Show>>
    suspend fun getTopShow(services: StreamingServices, showType: ShowType): Resource<List<Show>>
    suspend fun getShowsByTitle(title: String): Resource<List<Show>>

}
