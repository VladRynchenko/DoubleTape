package com.vroff.doubletape.detail

import com.vroff.domain.model.tmdb.search.MediaType
import kotlinx.serialization.Serializable

@Serializable
data object DetailsGraph

@Serializable
data class Details(
    val id: Int,
    val type: MediaType,
)

@Serializable
data class Profile(
    val id: Int,
)

@Serializable
object Video
