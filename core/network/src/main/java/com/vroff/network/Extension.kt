package com.vroff.network

import com.vroff.domain.model.NetworkResult
import kotlinx.coroutines.delay

suspend fun <T> retry(
    times: Int = 3,
    initialDelay: Long = 100,
    maxDelay: Long = 5000,
    block: suspend () -> NetworkResult<T>,
): NetworkResult<T> {
    var currentDelay = initialDelay
    repeat(times - 1) {
        val result = block()
        if (result is NetworkResult.Success) {
            return result
        }
        delay(currentDelay)
        currentDelay = (currentDelay * 2).coerceAtMost(maxDelay)
    }
    return block()
}
