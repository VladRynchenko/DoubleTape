package com.vroff.doubletape.storage.room.details.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCompanyEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.movie.CreditsWithDetail
import com.vroff.doubletape.storage.room.details.movie.MovieVideo
import com.vroff.doubletape.storage.room.details.movie.MovieWithDetails
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieGenreCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.movie.crossrefs.MovieVideoCrossRef
import com.vroff.doubletape.storage.room.details.movie.entity.BelongsToCollectionEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CastEntity
import com.vroff.doubletape.storage.room.details.movie.entity.CrewEntity
import com.vroff.doubletape.storage.room.details.movie.entity.MovieEntity
import com.vroff.doubletape.storage.room.details.series.AggregatedCreditsWithDetail
import com.vroff.doubletape.storage.room.details.series.SeriesVideo
import com.vroff.doubletape.storage.room.details.series.SeriesWithDetails
import com.vroff.doubletape.storage.room.details.series.crossrefs.NetworkCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesCreatedByCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesGenreCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.series.crossrefs.SeriesVideoCrossRef
import com.vroff.doubletape.storage.room.details.series.entity.CreatedByEntity
import com.vroff.doubletape.storage.room.details.series.entity.LastEpisodeEntity
import com.vroff.doubletape.storage.room.details.series.entity.NetworksEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeasonEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCastEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesCrewEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesJobEntity
import com.vroff.doubletape.storage.room.details.series.entity.SeriesRoleEntity

@Dao
interface CacheDao {
    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM series_entity WHERE id = :seriesId AND language = :language")
    suspend fun getAggregatedCredits(
        seriesId: Int,
        language: String,
    ): AggregatedCreditsWithDetail?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM movie_details WHERE id = :movieId AND language = :language")
    suspend fun getCredits(
        movieId: Int,
        language: String,
    ): CreditsWithDetail?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM series_entity WHERE id = :seriesId AND language = :language")
    suspend fun getSeriesVideos(
        seriesId: Int,
        language: String,
    ): SeriesVideo?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM movie_details WHERE id = :movieId AND language = :language")
    suspend fun getMovieVideos(
        movieId: Int,
        language: String,
    ): MovieVideo?

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    suspend fun insertAggregatedCredits(
        seriesId: Int,
        language: String,
        cast: List<SeriesCastEntity>,
        crew: List<SeriesCrewEntity>,
        roles: List<SeriesRoleEntity>,
        job: List<SeriesJobEntity>,
    ) {
        insertSeriesCast(cast)
        insertSeriesCrew(crew)
        insertJob(job)
        insertRole(roles)
    }

    @Transaction
    suspend fun insertMovieData(
        movie: MovieEntity,
        genres: List<GenreEntity>,
        languages: List<SpokenLanguageEntity>,
        belongToCollection: BelongsToCollectionEntity?,
        productionCompanies: List<ProductionCompanyEntity>,
        productionCountries: List<ProductionCountryEntity>,
    ) {
        val movieId = movie.id
        val productionCompanyCrossRefs =
            productionCompanies.map { MovieProductionCompanyCrossRef(movieId, it.id) }
        val productionCountryCrossRefs =
            productionCountries.map { MovieProductionCountryCrossRef(movieId, it.iso31661) }

        val genreCrossRefs = genres.map { MovieGenreCrossRef(movieId, it.id) }
        val languageCrossRefs =
            languages.map { MovieSpokenLanguageCrossRef(movieId, it.iso6391) }

        insertMovie(movie)
        insertGenres(genres)
        insertMovieGenreCrossRefs(genreCrossRefs)
        insertSpokenLanguages(languages)
        insertMovieSpokenLanguageCrossRefs(languageCrossRefs)
        insertProductionCompany(productionCompanies)
        insertMovieProductionCompanyCrossRefs(productionCompanyCrossRefs)
        insertProductionCountry(productionCountries)
        insertMovieProductionCountryCrossRefs(productionCountryCrossRefs)
        belongToCollection?.let { insertBelongToCollection(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBelongToCollection(belongToCollection: BelongsToCollectionEntity)

    @Transaction
    suspend fun insertSeriesData(
        series: SeriesEntity,
        genres: List<GenreEntity>,
        createdBy: List<CreatedByEntity>,
        languages: List<SpokenLanguageEntity>,
        productionCompanies: List<ProductionCompanyEntity>,
        productionCountries: List<ProductionCountryEntity>,
        seasons: List<SeasonEntity>?,
        lastEpisode: LastEpisodeEntity?,
        networks: List<NetworksEntity>,
    ) {
        val seriesId = series.id
        insertSeries(series)
        insertGenres(genres)
        val genreCrossRefs = genres.map { SeriesGenreCrossRef(seriesId, it.id) }
        val productionCompanyCrossRef =
            productionCompanies.map { SeriesProductionCompanyCrossRef(seriesId, it.id) }
        val languageCrossRefs =
            languages.map {
                SeriesSpokenLanguageCrossRef(seriesId, it.iso6391)
            }
        val productionCountryCrossRef =
            productionCountries.map { SeriesProductionCountryCrossRef(seriesId, it.iso31661) }

        insertSeriesGenreCrossRefs(genreCrossRefs)
        insertSpokenLanguages(languages)
        insertSeriesSpokenLanguageCrossRefs(languageCrossRefs)
        insertProductionCompany(productionCompanies)
        insertSeriesProductionCompanyCrossRefs(productionCompanyCrossRef)
        insertProductionCountry(productionCountries)
        insertSeriesProductionCountryCrossRefs(productionCountryCrossRef)
        seasons?.let { insertSeasons(it) }
        insertCreatedBy(createdBy)
        insertSeriesCreatedByCrossRefs(
            createdBy.map { SeriesCreatedByCrossRef(seriesId, it.id) },
        )
        insertNetworks(networks)
        lastEpisode?.let { insertLastEpisodeToAir(lastEpisode) }
        insertNetworkCrossRefs(
            networks.map { NetworkCrossRef(seriesId, it.id) },
        )
    }

    @Transaction
    suspend fun insertCredits(
        cast: List<CastEntity>?,
        crew: List<CrewEntity>?,
    ) {
        cast?.let { insertCast(cast) }
        crew?.let { insertCrew(crew) }
    }

    @Transaction
    suspend fun insertMovieVideo(
        id: Int,
        videos: List<VideoEntity>?,
    ) {
        videos?.let {
            insertVideos(videos)
            insertMovieVideosCrossRef(videos.map { MovieVideoCrossRef(id, it.id) })
        }
    }

    @Transaction
    suspend fun insertSeriesVideo(
        id: Int,
        videos: List<VideoEntity>?,
    ) {
        videos?.let {
            insertVideos(videos)
            insertSeriesVideosCrossRef(videos.map { SeriesVideoCrossRef(id, it.id) })
        }
    }

    @Transaction
    @Query("SELECT * FROM movie_details WHERE id = :movieId AND language = :language")
    suspend fun getMovie(
        movieId: Int,
        language: String,
    ): MovieWithDetails?

    @Transaction
    @Query("SELECT * FROM series_entity WHERE id = :seriesId AND language = :language")
    suspend fun getSeries(
        seriesId: Int,
        language: String,
    ): SeriesWithDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(seriesEntity: SeriesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRefs(crossRefs: List<MovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLastEpisodeToAir(lastEpisodeToAirEntity: LastEpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesGenreCrossRefs(crossRefs: List<SeriesGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguages(languages: List<SpokenLanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSpokenLanguageCrossRefs(crossRefs: List<MovieSpokenLanguageCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesSpokenLanguageCrossRefs(crossRefs: List<SeriesSpokenLanguageCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCast(cast: List<CastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrew(crew: List<CrewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompany(productionCompanies: List<ProductionCompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieProductionCompanyCrossRefs(productionCompanies: List<MovieProductionCompanyCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesProductionCompanyCrossRefs(productionCompanies: List<SeriesProductionCompanyCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountry(productionCountry: List<ProductionCountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieProductionCountryCrossRefs(productionCountry: List<MovieProductionCountryCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesProductionCountryCrossRefs(productionCountry: List<SeriesProductionCountryCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesCrew(seriesCrew: List<SeriesCrewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesCast(seriesCast: List<SeriesCastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(jobs: List<SeriesJobEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRole(roles: List<SeriesRoleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieVideosCrossRef(crossRefs: List<MovieVideoCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeriesVideosCrossRef(crossRefs: List<SeriesVideoCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(seasons: List<SeasonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesCreatedByCrossRefs(createdByCrossRefs: List<SeriesCreatedByCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreatedBy(createdBy: List<CreatedByEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetworkCrossRefs(networkCrossRefs: List<NetworkCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetworks(networks: List<NetworksEntity>)
}
