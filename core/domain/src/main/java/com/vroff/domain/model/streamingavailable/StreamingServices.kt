package com.vroff.domain.model.streamingavailable

sealed class StreamingServices(
    val name: String,
) {
    data object Netflix : StreamingServices("netflix")

    data object Prime : StreamingServices("prime")

    data object Disney : StreamingServices("disney")

    data object Apple : StreamingServices("apple")

    data object Hbo : StreamingServices("hbo")
}
