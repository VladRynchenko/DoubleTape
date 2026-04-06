package com.vroff.doubletape.storage.room.details

import com.vroff.domain.model.tmdb.movie.MovieAppendedToResponse
import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.model.tmdb.movie.SeriesAppendedToResponse
import com.vroff.domain.model.tmdb.series.SeriesDetail
import com.vroff.domain.repository.CacheRepository
import com.vroff.domain.util.coRunCatching
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.toEntity
import com.vroff.doubletape.storage.room.details.dao.CacheDao
import com.vroff.doubletape.storage.room.details.movie.entity.toDatabase
import com.vroff.doubletape.storage.room.details.movie.entity.toEntity
import com.vroff.doubletape.storage.room.details.series.entity.toEntity
import javax.inject.Inject

class CacheRepositoryImpl
    @Inject
    constructor(
        private val cacheDao: CacheDao,
    ) : CacheRepository {
        override suspend fun getMovie(
            movieId: Int,
            language: String,
            appendToResponse: List<MovieAppendedToResponse>,
        ): Result<MovieDetail> {
            return coRunCatching {
                val cachedMovie = cacheDao.getMovie(movieId, language)
                if (cachedMovie?.movie?.isDataValid(86400) == true) {
                    val credits =
                        (MovieAppendedToResponse.CREDITS in appendToResponse).let {
                            cacheDao.getCredits(movieId, language)?.toDomain()
                        }
                    val video =
                        (MovieAppendedToResponse.VIDEOS in appendToResponse).let {
                            cacheDao.getMovieVideos(movieId, language)?.videos?.map { it.toDomain() }
                        }
                    return Result.success(cachedMovie.toDomain(credits, video))
                } else {
                    throw NoSuchElementException("Valid movie not found in cache")
                }
            }
        }

        override suspend fun getSeries(
            seriesId: Int,
            language: String,
            appendToResponse: List<SeriesAppendedToResponse>,
        ): Result<SeriesDetail> {
            return coRunCatching {
                val cachedSeries = cacheDao.getSeries(seriesId, language)
                if (cachedSeries?.series?.isDataValid(86400) == true) {
                    val aggregated =
                        (SeriesAppendedToResponse.AGGREGATE_CREDITS in appendToResponse).let {
                            cacheDao.getAggregatedCredits(seriesId, language)?.toDomain()
                        }
                    val videos =
                        (SeriesAppendedToResponse.VIDEOS in appendToResponse).let {
                            cacheDao.getSeriesVideos(seriesId, language)?.videos?.map { it.toDomain() }
                        }
                    return Result.success(cachedSeries.toDomain(aggregated, videos))
                } else {
                    throw NoSuchElementException("Valid series not found in cache")
                }
            }
        }

        override suspend fun saveSeriesToCache(
            domain: SeriesDetail,
            language: String,
            appendToResponse: List<SeriesAppendedToResponse>,
        ) {
            val seriesId = domain.id
            val seriesEntity = domain.toEntity(language = language)

            val genreEntities = domain.genres.map { GenreEntity(it.id, it.name) }

            val languageEntities =
                domain.spokenLanguages.map {
                    SpokenLanguageEntity(it.iso6391, it.englishName, it.name)
                }
            val productionCompanyEntities = domain.productionCompanies.map { it.toEntity() }

            val createdByEntity = domain.createdBy.map { it.toEntity(seriesId) }

            val productionCountryEntities =
                domain.productionCountries.map {
                    ProductionCountryEntity(
                        iso31661 = it.iso31661,
                        name = it.name,
                    )
                }

            cacheDao.insertSeriesData(
                seriesEntity,
                genreEntities,
                createdByEntity,
                languageEntities,
                productionCompanyEntities,
                productionCountryEntities,
                domain.seasons.map { it.toEntity(seriesId) },
                domain.lastEpisodeToAir?.toEntity(seriesId),
                domain.networks.map { it.toEntity(seriesId) },
            )

            (SeriesAppendedToResponse.AGGREGATE_CREDITS in appendToResponse).let {
                cacheDao.insertAggregatedCredits(
                    seriesId,
                    language,
                    domain.aggregateCredits?.cast?.map { it.toEntity(seriesId) } ?: emptyList(),
                    domain.aggregateCredits?.crew?.map { it.toEntity(seriesId) } ?: emptyList(),
                    domain.aggregateCredits
                        ?.cast
                        ?.flatMap {
                            it.roles
                                ?.map { role ->
                                    role.toEntity(castId = it.id)
                                }.orEmpty()
                        }.orEmpty(),
                    domain.aggregateCredits
                        ?.crew
                        ?.flatMap {
                            it.jobs.map { job ->
                                job.toEntity(crewId = it.id)
                            }
                        }.orEmpty(),
                )
            }

            (SeriesAppendedToResponse.VIDEOS in appendToResponse).let {
                cacheDao.insertSeriesVideo(
                    seriesId,
                    domain.videos?.toEntity(),
                )
            }
        }

        override suspend fun saveMovieToCache(
            domain: MovieDetail,
            language: String,
            appendToResponse: List<MovieAppendedToResponse>,
        ) {
            val movieId = domain.id
            val movieEntity = domain.toDatabase(language = language)

            val genreEntities = domain.genres.map { GenreEntity(it.id, it.name) }
            val belongsToCollectionEntity = domain.belongsToCollection?.toEntity(movieId)

            val languageEntities = domain.spokenLanguages.map { it.toEntity() }
            val productionCompanyEntities = domain.productionCompanies.map { it.toEntity() }
            val productionCountryEntities = domain.productionCountries.map { it.toEntity() }

            cacheDao.insertMovieData(
                movieEntity,
                genreEntities,
                languageEntities,
                belongsToCollectionEntity,
                productionCompanyEntities,
                productionCountryEntities,
            )

            (MovieAppendedToResponse.VIDEOS in appendToResponse).let {
                cacheDao.insertMovieVideo(
                    movieId,
                    domain.videos?.toEntity(),
                )
            }

            (MovieAppendedToResponse.CREDITS in appendToResponse).let {
                val castEntities = domain.credits?.cast?.map { it.toEntity(movieId) }
                val crewEntities = domain.credits?.crew?.map { it.toEntity(movieId) }
                cacheDao.insertCredits(castEntities, crewEntities)
            }
        }
    }
