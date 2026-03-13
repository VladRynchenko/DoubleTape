package com.vroff.data.usecase

import com.vroff.domain.repository.TMDBRepository
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val repository: TMDBRepository
) {
    suspend fun execute() = repository.getConfiguration()
}