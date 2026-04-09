package com.vroff.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class TimedPagingSource<Dto : Any, Domain : Any>(
    private val request: suspend (page: Int) -> TimedPagerResponse<Dto>?,
    private val mapper: (Dto) -> Domain,
) : PagingSource<Int, Domain>() {
    var timePeriodFromResponse: TimePeriod? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Domain> {
        val page = params.key ?: 1
        return try {
            val response = request(page) ?: return LoadResult.Error(Exception("Response is null"))
            timePeriodFromResponse = response.dates

            LoadResult.Page(
                data = response.results.map(mapper),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Domain>): Int? = state.anchorPosition
}
