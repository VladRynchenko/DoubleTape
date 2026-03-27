package com.vroff.network.paging

import com.google.gson.annotations.SerializedName

data class TimedPagerResponse<T>(
    val results: List<T>,
    val dates: TimePeriod,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
)
