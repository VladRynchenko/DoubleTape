package com.vroff.doubletape.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface DoubleTapeDataStore {


    suspend fun saveString(key: Keys, value: String)

    fun getString(key: Keys): Flow<String?>
    fun getStringOrDefault(key: Keys, defaultValue: String): Flow<String>

    suspend fun <T> saveSerialize(key: Keys, value: T, serializer: KSerializer<T>)
    fun <T> getSerializeOrDefault(key: Keys, serializer: KSerializer<T>, defaultValue: T): Flow<T>
    fun <T> getSerialize(key: Keys, serializer: KSerializer<T>): Flow<T?>


    sealed class Keys(val key: String) {
        data object Configuration : Keys("configuration")
    }

}