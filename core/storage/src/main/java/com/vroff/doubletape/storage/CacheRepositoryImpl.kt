package com.vroff.doubletape.storage

import com.vroff.domain.model.tmdb.movie.MovieDetail
import com.vroff.domain.repository.CacheRepository
import com.vroff.doubletape.storage.room.CacheDao
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.common.toEntity
import com.vroff.doubletape.storage.room.details.movie.MovieGenreCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.movie.toDatabase
import com.vroff.doubletape.storage.room.details.movie.toEntity
import javax.inject.Inject

class CacheRepositoryImpl
    @Inject
    constructor(
        private val cacheDao: CacheDao,
    ) : CacheRepository {
        override suspend fun getMovie(
            movieId: Int,
            language: String,
        ): Result<MovieDetail> {
            val cachedEntry =
                try {
                    cacheDao.getMovie(movieId, language)
                } catch (e: Exception) {
                    return Result.failure(e)
                }
            if (cachedEntry?.movie?.isDataValid() == true) {
                return Result.success(cachedEntry.toDomain())
            }
            return Result.failure(NoSuchElementException("Valid movie not found in cache"))
        }

        override suspend fun saveMovieToCache(
            domain: MovieDetail,
            language: String,
        ) {
            val movieId = domain.id
            val movieEntity = domain.toDatabase(language = language)

            val genreEntities = domain.genres.map { GenreEntity(it.id, it.name) }
            val genreCrossRefs = domain.genres.map { MovieGenreCrossRef(movieId, it.id) }

            val languageEntities =
                domain.spokenLanguages.map {
                    SpokenLanguageEntity(it.iso6391, it.englishName, it.name)
                }
            val languageCrossRefs =
                domain.spokenLanguages.map {
                    MovieSpokenLanguageCrossRef(movieId, it.iso6391)
                }
            val productionCompanyEntities = domain.productionCompanies.map { it.toEntity() }
            val productionCompanyCrossRef =
                domain.productionCompanies.map { MovieProductionCompanyCrossRef(movieId, it.id) }

            val productionCountryEntities =
                domain.productionCountries.map {
                    ProductionCountryEntity(
                        iso31661 = it.iso31661,
                        name = it.name,
                    )
                }
            val movieProductionCountryCrossRef =
                domain.productionCountries.map { MovieProductionCountryCrossRef(movieId, it.iso31661) }

            val castEntities = domain.credits?.cast?.map { it.toEntity(movieId) }
            val crewEntities = domain.credits?.crew?.map { it.toEntity(movieId) }

            val videoEntities =
                domain.videos?.map {
                    VideoEntity(
                        id = it.id,
                        movieId = movieId,
                        iso6391 = it.iso6391,
                        iso31661 = it.iso31661,
                        name = it.name,
                        key = it.key,
                        site = it.site,
                        size = it.size,
                        type = it.type,
                        official = it.official,
                        publishedAt = it.publishedAt,
                    )
                }

            cacheDao.insertFullMovieData(
                movieEntity,
                genreEntities,
                genreCrossRefs,
                languageEntities,
                languageCrossRefs,
                productionCompanyEntities,
                productionCompanyCrossRef,
                productionCountryEntities,
                movieProductionCountryCrossRef,
                castEntities,
                crewEntities,
                videoEntities,
            )
        }
    }
