package com.vroff.doubletape.storage

import com.vroff.domain.repository.RecentSearchRepository
import com.vroff.doubletape.storage.room.SearchDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSearchRepositoryImpl
    @Inject
    constructor(
        private val recentSearchDao: SearchDao,
    ) : RecentSearchRepository {
        override suspend fun addRecentSearch(query: String) {
            recentSearchDao.insertWithTrimOldQueries(query)
        }

        override suspend fun clearRecentSearch() {
            recentSearchDao.cleanRecentSearches()
        }

        override fun getRecentSearches(): Flow<List<String>> =
            recentSearchDao.getRecent().map { entities ->
                entities.map { it.searchQuery }
            }
    }
