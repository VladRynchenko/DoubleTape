package com.vroff.tmdb.entity.common

import com.google.gson.annotations.SerializedName
import com.vroff.domain.model.tmdb.common.SpokenLanguage

data class SpokenLanguageDTO(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String,
) {
    fun mapToDomain(): SpokenLanguage =
        SpokenLanguage(
            englishName = englishName,
            iso6391 = iso6391,
            name = name,
        )
}
