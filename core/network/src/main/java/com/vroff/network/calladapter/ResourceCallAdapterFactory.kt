package com.vroff.network.calladapter

import com.vroff.domain.model.streamingavailable.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResourceCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type?,
        annotations: Array<out Annotation?>?,
        retrofit: Retrofit?,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(responseType) != NetworkResult::class.java) return null

        return NetworkResultCallAdapter<Any>(getParameterUpperBound(0, responseType as ParameterizedType))
    }
}
