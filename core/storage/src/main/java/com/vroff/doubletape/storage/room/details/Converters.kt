package com.vroff.doubletape.storage.room.details

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    private val json =
        Json {
            ignoreUnknownKeys = true
        }

    @TypeConverter
    fun fromStringList(list: List<String>?): String = json.encodeToString(list ?: emptyList())

    @TypeConverter
    fun toStringList(data: String?): List<String> {
        if (data.isNullOrEmpty()) return emptyList()
        return json.decodeFromString(data)
    }

    @TypeConverter
    fun fromIntList(list: List<Int>?): String = json.encodeToString(list ?: emptyList())

    @TypeConverter
    fun toIntList(data: String?): List<Int> {
        if (data.isNullOrEmpty()) return emptyList()
        return json.decodeFromString(data)
    }
}
