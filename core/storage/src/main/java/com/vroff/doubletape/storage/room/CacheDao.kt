package com.vroff.doubletape.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vroff.doubletape.storage.room.details.common.GenreEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCompanyEntity
import com.vroff.doubletape.storage.room.details.common.ProductionCountryEntity
import com.vroff.doubletape.storage.room.details.common.SpokenLanguageEntity
import com.vroff.doubletape.storage.room.details.common.VideoEntity
import com.vroff.doubletape.storage.room.details.movie.CastEntity
import com.vroff.doubletape.storage.room.details.movie.CrewEntity
import com.vroff.doubletape.storage.room.details.movie.MovieEntity
import com.vroff.doubletape.storage.room.details.movie.MovieGenreCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieProductionCompanyCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieProductionCountryCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieSpokenLanguageCrossRef
import com.vroff.doubletape.storage.room.details.movie.MovieWithDetails

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenreCrossRefs(crossRefs: List<MovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguages(languages: List<SpokenLanguageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguageCrossRefs(crossRefs: List<MovieSpokenLanguageCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCast(cast: List<CastEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrew(crew: List<CrewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompany(productionCompanies: List<ProductionCompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompanyCrossRefs(productionCompanies: List<MovieProductionCompanyCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountry(productionCountry: List<ProductionCountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountryCrossRefs(productionCountry: List<MovieProductionCountryCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Transaction
    suspend fun insertFullMovieData(
        movie: MovieEntity,
        genres: List<GenreEntity>,
        genreCrossRefs: List<MovieGenreCrossRef>,
        languages: List<SpokenLanguageEntity>,
        languageCrossRefs: List<MovieSpokenLanguageCrossRef>,
        productionCompanies: List<ProductionCompanyEntity>,
        productionCompanyCrossRefs: List<MovieProductionCompanyCrossRef>,
        productionCountries: List<ProductionCountryEntity>,
        movieProductionCountryCrossRefs: List<MovieProductionCountryCrossRef>,
        cast: List<CastEntity>?,
        crew: List<CrewEntity>?,
        videos: List<VideoEntity>?,
    ) {
        insertMovie(movie)
        insertGenres(genres)
        insertGenreCrossRefs(genreCrossRefs)
        insertSpokenLanguages(languages)
        insertSpokenLanguageCrossRefs(languageCrossRefs)
        cast?.let { insertCast(cast) }
        crew?.let { insertCrew(crew) }
        insertProductionCompany(productionCompanies)
        insertProductionCompanyCrossRefs(productionCompanyCrossRefs)
        insertProductionCountry(productionCountries)
        insertProductionCountryCrossRefs(movieProductionCountryCrossRefs)
        videos?.let { insertVideos(videos) }
    }

    @Transaction
    @Query("SELECT * FROM movie_details WHERE id = :movieId AND language = :language")
    suspend fun getMovie(
        movieId: Int,
        language: String,
    ): MovieWithDetails?
}
