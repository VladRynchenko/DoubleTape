package com.vroff.data.usecase

import com.vroff.domain.model.home.MainScreenContent
import com.vroff.domain.repository.TrendingRepository
import com.vroff.domain.util.coRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.supervisorScope
import java.util.Locale
import javax.inject.Inject

class GetMainScreenDataUseCase
    @Inject
    constructor(
        private val trendingRepository: TrendingRepository,
        private val locale: Locale,
    ) {
        suspend fun execute(): Flow<Result<MainScreenContent>> =
            supervisorScope {
                val language = locale.language
                val region = locale.country

                combine(
                    trendingRepository.getUpcomingMoviePreview(language, region),
                    trendingRepository.getPopularMoviePreview(language, region),
                    trendingRepository.getNowPlayingMoviePreview(language, region),
                ) { upcoming, popular, nowPlaying ->
                    coRunCatching {
                        MainScreenContent(
                            nowPlayingMovie = nowPlaying.getOrThrow(),
                            popularMovie = popular.getOrThrow(),
                            upcomingMovie = upcoming.getOrThrow(),
                        )
                    }
                }
            }
    }
