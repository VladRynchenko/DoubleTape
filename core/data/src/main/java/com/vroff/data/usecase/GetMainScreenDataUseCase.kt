package com.vroff.data.usecase

import com.vroff.domain.model.home.MainScreenContent
import com.vroff.domain.repository.TrendingRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import java.util.Locale
import javax.inject.Inject

class GetMainScreenDataUseCase
    @Inject
    constructor(
        private val trendingRepository: TrendingRepository,
        private val locale: Locale,
    ) {
        suspend fun execute(): MainScreenContent =
            supervisorScope {
                val language = locale.language
                val region = locale.country

                val getUpcomingDeferred =
                    async {
                        trendingRepository.getUpcomingMoviePreview(language, region)
                    }

                val popularDeferred =
                    async {
                        trendingRepository.getPopularMoviePreview(language, region)
                    }

                val nowPlayingDeferred =
                    async {
                        trendingRepository.getNowPlayingMoviePreview(language, region)
                    }

                val nowPlaying = nowPlayingDeferred.await().getOrNull()
                val popular = popularDeferred.await().getOrNull()
                val upcoming = getUpcomingDeferred.await().getOrNull()
                MainScreenContent(
                    nowPlayingMovie = nowPlaying,
                    popularMovie = popular,
                    upcomingMovie = upcoming,
                )
            }
    }
