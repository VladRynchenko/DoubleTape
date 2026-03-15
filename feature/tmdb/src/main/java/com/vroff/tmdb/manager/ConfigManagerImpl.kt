package com.vroff.tmdb.manager

import com.vroff.domain.model.streamingavailable.NetworkResult
import com.vroff.domain.model.tmdb.TMDBConfiguration
import com.vroff.domain.repository.ConfigManager
import com.vroff.domain.storage.DoubleTapeDataStore
import com.vroff.domain.util.safeApiCall
import com.vroff.domain.util.saveToDataStore
import com.vroff.tmdb.api.TMDBApi
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConfigManagerImpl
    @Inject
    constructor(
        private val api: TMDBApi,
        private val storage: DoubleTapeDataStore,
    ) : ConfigManager {
        override var config: TMDBConfiguration? = null

        override suspend fun loadConfig() {
            if (config != null || isConfigurationExpired(config).not()) return
            storage
                .get<TMDBConfiguration>(DoubleTapeDataStore.Keys.Configuration)
                .map { configuration ->
                    if (configuration == null || isConfigurationExpired(configuration)) {
                        api
                            .getConfiguration()
                            .safeApiCall { it.mapToDomain() }
                            .saveToDataStore {
                                storage.save(DoubleTapeDataStore.Keys.Configuration, it)
                            }
                    } else {
                        NetworkResult.Success(configuration)
                    }
                }.collect {
                    if (it is NetworkResult.Success) {
                        config = it.data
                    }
                }
        }

        fun isConfigurationExpired(configuration: TMDBConfiguration?): Boolean {
            if (configuration == null) return true
            val currentTime = System.currentTimeMillis() / 1000
            val expirationTime = configuration.timestampSeconds
            return currentTime - expirationTime > 24 * 60 * 60
        }
    }
