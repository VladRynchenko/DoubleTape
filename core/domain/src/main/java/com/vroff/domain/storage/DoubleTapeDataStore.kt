package com.vroff.domain.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

abstract class DoubleTapeDataStore {


    abstract suspend fun saveString(key: Keys, value: String)

    abstract fun getString(key: Keys): Flow<String?>
    abstract fun getStringOrDefault(key: Keys, defaultValue: String): Flow<String>

    abstract suspend fun <T> saveSerialize(key: Keys, value: T, serializer: KSerializer<T>)
    abstract fun <T> getSerializeOrDefault(key: Keys, serializer: KSerializer<T>, defaultValue: T): Flow<T>
    abstract fun <T> getSerialize(key: Keys, serializer: KSerializer<T>): Flow<T?>


    suspend inline fun <reified T> save(key: Keys, value: T) {
        saveSerialize(key, value, serializer<T>())
    }

    inline fun <reified T> get(key: Keys): Flow<T?> {
        return getSerialize(key, serializer<T>())
    }

    sealed class Keys(val key: String) {
        data object Configuration : Keys("configuration")
    }

}