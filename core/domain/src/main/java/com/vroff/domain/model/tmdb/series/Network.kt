package com.vroff.domain.model.tmdb.series

import com.vroff.domain.model.LogoImage

data class Network(
    val id: Int,
    val logoImage: LogoImage?,
    val name: String,
    val originCountry: String,
)
