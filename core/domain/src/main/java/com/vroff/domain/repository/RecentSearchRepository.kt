package com.vroff.domain.repository

import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    fun getRecentSearches(): Flow<List<String>>

    suspend fun addRecentSearch(query: String)

    suspend fun clearRecentSearch()
}
