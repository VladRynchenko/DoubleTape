package com.vroff.doubletape.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vroff.doubletape.storage.entity.RecentSearchEntity

@Database(entities = [RecentSearchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}
