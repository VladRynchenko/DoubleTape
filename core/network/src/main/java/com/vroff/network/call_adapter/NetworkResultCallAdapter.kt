package com.vroff.network.call_adapter

import com.vroff.domain.model.streaming_available.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter<T>(
    private val resultType: Type
) : CallAdapter<T, Call<NetworkResult<T>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<T>): Call<NetworkResult<T>> {
        return NetworkResultCall(call)
    }
}