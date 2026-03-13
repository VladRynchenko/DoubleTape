package com.vroff.domain.repository

import com.vroff.domain.models.Show
import com.vroff.domain.models.ShowType
import com.vroff.domain.models.StreamingServices
import com.vroff.domain.util.Resource

interface ShowRepository {

    suspend fun getShow(id: String): Resource<Show>
    suspend fun getShows(): Resource<List<Show>>
    suspend fun getTopShow(services: StreamingServices, showType: ShowType): Resource<List<Show>>
    suspend fun getShowsByTitle(title: String): Resource<List<Show>>

}
