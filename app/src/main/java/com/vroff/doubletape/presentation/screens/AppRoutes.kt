package com.vroff.doubletape.presentation.screens

import com.vroff.domain.model.tmdb.search.MediaType
import kotlinx.serialization.Serializable

@Serializable
sealed class Graph {
    @Serializable
    data object Main : Graph()

    @Serializable
    data object Search : Graph()

    @Serializable
    data class Details(
        val id: Int,
        val type: MediaType
    ) : Graph()
}

