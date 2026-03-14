package com.vroff.network

import coil3.EventListener
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult

class CoilLogger : EventListener() {

    override fun onStart(request: ImageRequest) {
        println("Coil START: ${request.data}")
    }

    override fun onSuccess(request: ImageRequest, result: SuccessResult) {
        println("Coil SUCCESS: ${request.data}")
    }

    override fun onError(request: ImageRequest, result: ErrorResult) {
        println("Coil ERROR: ${request.data}")
        result.throwable.printStackTrace()
    }
}