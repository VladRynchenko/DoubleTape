package com.vroff.doubletape.storage.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches", indices = [Index(value = ["searchQuery"], unique = true)])
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val searchQuery: String,
    val timestamp: Long = System.currentTimeMillis(),
)
