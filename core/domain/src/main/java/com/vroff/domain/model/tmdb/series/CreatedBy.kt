package com.vroff.domain.model.tmdb.series

data class CreatedBy(
    val id: Long,
    val creditId: String,
    val name: String,
    val gender: Long,
    val profilePath: String,
)
