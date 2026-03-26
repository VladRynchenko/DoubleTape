package com.vroff.network.paging

import com.google.gson.annotations.SerializedName

data class TimePeriod(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String,
)
