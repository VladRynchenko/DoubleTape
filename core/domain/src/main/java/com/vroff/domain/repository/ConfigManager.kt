package com.vroff.domain.repository

import com.vroff.domain.model.tmdb.TMDBConfiguration

interface ConfigManager {
    val config : TMDBConfiguration?
    suspend fun loadConfig()
}