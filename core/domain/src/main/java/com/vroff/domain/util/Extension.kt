package com.vroff.domain.util

import com.vroff.domain.model.streaming_available.NetworkResult

inline fun <T, R> NetworkResult<T>.safeApiCall(
    transform: (T) -> R
): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(data))
        is NetworkResult.Error -> NetworkResult.Error(code, message)
        is NetworkResult.Exception -> NetworkResult.Exception(e)
    }
}

inline fun <R> NetworkResult<R>.saveToDataStore(
    save: (R) -> Unit
): NetworkResult<R> {
    if (this is NetworkResult.Success) {
        save(data)
    }
    return this
}
