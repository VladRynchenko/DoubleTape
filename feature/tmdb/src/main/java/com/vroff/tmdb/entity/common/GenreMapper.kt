package com.vroff.tmdb.entity.common

import com.vroff.domain.model.tmdb.common.Genre
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.repository.GenresManager
import javax.inject.Inject

class GenreMapper
    @Inject
    constructor(
        val genresManager: GenresManager,
    ) {
        fun map(
            genreIds: List<Long>?,
            mediaType: MediaType,
        ): List<Genre> {
            if (genreIds.isNullOrEmpty()) return emptyList()

            val genreMap =
                when (mediaType) {
                    MediaType.MOVIE -> genresManager.movieGenres
                    MediaType.SERIES -> genresManager.seriesGenres
                    else -> null
                }?.associateBy { it.id }

            return genreIds.mapNotNull { id ->
                genreMap?.get(id)
            }
        }
    }
