package com.vroff.domain.util

import com.vroff.domain.model.NetworkResult

inline fun <T, R> NetworkResult<T>.safeApiCall(transform: (T) -> R): Result<R> =
    when (this) {
        is NetworkResult.Success -> Result.success(transform(data))
        is NetworkResult.Error -> Result.failure(Exception(message))
        is NetworkResult.Exception -> Result.failure(e)
    }

inline fun <R> NetworkResult<R>.onSuccess(function: (R) -> Unit): NetworkResult<R> {
    if (this is NetworkResult.Success) {
        function(data)
    }
    return this
}

inline fun <R> NetworkResult<R>.onSuccessCatching(function: (R) -> Unit): NetworkResult<R> =
    if (this is NetworkResult.Success) {
        try {
            function(data)
            this
        } catch (e: Exception) {
            NetworkResult.Exception(e)
            throw e
        }
    } else {
        this
    }

fun <T> NetworkResult<T>.getOrNull(): T? = if (this is NetworkResult.Success) data else null
