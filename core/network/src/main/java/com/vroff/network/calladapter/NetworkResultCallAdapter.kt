package com.vroff.network.calladapter

import com.vroff.domain.model.streamingavailable.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter<T>(
    private val resultType: Type,
) : CallAdapter<T, Call<NetworkResult<T>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<T>): Call<NetworkResult<T>> = NetworkResultCall(call)
}
