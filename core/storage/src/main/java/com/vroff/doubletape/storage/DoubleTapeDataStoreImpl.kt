package com.vroff.doubletape.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DoubleTapeDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DoubleTapeDataStore {

    private val json: Json = Json { ignoreUnknownKeys = true }

    override suspend fun saveString(
        key: DoubleTapeDataStore.Keys,
        value: String
    ) {
        dataStore.edit { preferences ->
            runCatching { preferences[stringPreferencesKey(key.key)] = value }
        }
    }

    override fun getString(key: DoubleTapeDataStore.Keys): Flow<String?> {
        val preferences = dataStore.data
        return preferences.map { preferences ->
            runCatching { preferences[stringPreferencesKey(key.key)] }.getOrNull()
        }
    }

    override fun getStringOrDefault(
        key: DoubleTapeDataStore.Keys,
        defaultValue: String
    ): Flow<String> {
        return getString(key).map { it ?: defaultValue }
    }

    override suspend fun <T> saveSerialize(
        key: DoubleTapeDataStore.Keys,
        value: T,
        serializer: KSerializer<T>
    ) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key.key)] = json.encodeToString(serializer, value)
        }
    }

    override fun <T> getSerializeOrDefault(
        key: DoubleTapeDataStore.Keys,
        serializer: KSerializer<T>,
        defaultValue: T
    ): Flow<T> {
        return getSerialize(key, serializer).map { it ?: defaultValue }
    }

    override fun <T> getSerialize(
        key: DoubleTapeDataStore.Keys,
        serializer: KSerializer<T>
    ): Flow<T?> {
        return dataStore.data.map { prefs ->
            val stringValue = prefs[stringPreferencesKey(key.key)]
            if (stringValue != null) {
                runCatching { json.decodeFromString(serializer, stringValue) }.getOrNull()
            } else {
                null
            }
        }
    }
}