package com.vroff.doubletape.storage.room.details

interface Cacheable {
    val updatedAt: Long

    fun isDataValid(lifetime: Int): Boolean {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - updatedAt
        val lifetimeInMillis = lifetime * 1000 * 60
        return timeDifference < lifetimeInMillis
    }
}
