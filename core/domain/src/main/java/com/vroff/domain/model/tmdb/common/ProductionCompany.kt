package com.vroff.domain.model.tmdb.common

import com.vroff.domain.model.LogoImage

data class ProductionCompany(
    val id: Int,
    val logoImage: LogoImage?,
    val name: String,
    val originCountry: String,
)
