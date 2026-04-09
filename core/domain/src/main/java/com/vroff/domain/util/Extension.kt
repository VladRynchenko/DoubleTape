package com.vroff.domain.util

import com.vroff.domain.model.NetworkResult
import kotlinx.coroutines.CancellationException

inline fun <T, R> NetworkResult<T>.toResult(transform: (T) -> R): Result<R> =
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

inline fun <R> NetworkResult<R>.onSuccessCatching(action: (R) -> Unit): NetworkResult<R> {
    val result = this as? NetworkResult.Success<R> ?: return this
    return try {
        action(result.data)
        this
    } catch (e: Exception) {
        NetworkResult.Exception(e)
    }
}

inline fun <T> coRunCatching(block: () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
