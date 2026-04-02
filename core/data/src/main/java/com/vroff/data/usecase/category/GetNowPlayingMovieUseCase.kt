package com.vroff.data.usecase.category

import androidx.paging.PagingData
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import com.vroff.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class GetNowPlayingMovieUseCase
    @Inject
    constructor(
        private val repository: TrendingRepository,
    ) {
        operator fun invoke(): Flow<PagingData<MovieMediaItem>> {
            val local = Locale.getDefault()
            return repository.getNowPlayingMovie(local.language, local.country)
        }

        suspend fun getPreview(): Result<List<MovieMediaItem>> {
            val local = Locale.getDefault()
            return repository.getNowPlayingMoviePreview(local.language, local.country)
        }
    }
