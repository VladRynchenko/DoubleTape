package com.vroff.domain.model.tmdb.common

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Long,
    val name: String,
)
