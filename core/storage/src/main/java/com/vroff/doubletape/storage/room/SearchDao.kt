package com.vroff.doubletape.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.vroff.doubletape.storage.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("UPDATE recent_searches SET timestamp = :timestamp WHERE searchQuery = :searchQuery")
    suspend fun updateTimestamp(
        searchQuery: String,
        timestamp: Long,
    ): Int

    @Insert
    suspend fun insert(query: RecentSearchEntity): Long

    @Query("SELECT * FROM recent_searches ORDER BY timestamp DESC LIMIT 20")
    fun getRecent(): Flow<List<RecentSearchEntity>>

    @Query(
        "DELETE FROM recent_searches WHERE id NOT IN (SELECT id FROM recent_searches ORDER BY timestamp DESC LIMIT 20)",
    )
    suspend fun trimOldQueries()

    @Transaction
    suspend fun insertWithTrimOldQueries(query: String) {
        val normalized = query.trim().lowercase()
        val now = System.currentTimeMillis()

        val updatedRows = updateTimestamp(normalized, now)

        if (updatedRows == 0) {
            val id =
                insert(
                    RecentSearchEntity(searchQuery = normalized, timestamp = now),
                )
            if (id != -1L) {
                trimOldQueries()
            }
        }
    }

    @Query("DELETE FROM recent_searches")
    suspend fun cleanRecentSearches()
}
