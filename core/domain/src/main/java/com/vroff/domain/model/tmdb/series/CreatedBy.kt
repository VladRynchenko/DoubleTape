package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.ProfileImage

data class CreatedBy(
    val id: Int,
    val creditId: String,
    val name: String,
    val gender: Int,
    val profileImage: ProfileImage?,
)
