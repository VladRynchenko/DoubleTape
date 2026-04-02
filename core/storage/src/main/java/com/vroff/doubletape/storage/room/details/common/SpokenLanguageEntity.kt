package com.vroff.doubletape.storage.room.details.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroff.domain.model.tmdb.common.SpokenLanguage

@Entity(tableName = "spoken_languages")
data class SpokenLanguageEntity(
    @PrimaryKey
    val iso6391: String,
    val englishName: String,
    val name: String,
) {
    fun toDomain(): SpokenLanguage = SpokenLanguage(englishName, iso6391, name)
}
