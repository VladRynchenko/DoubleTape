package com.vroff.streamingmovie.mapper

import com.vroff.domain.model.streaming_available.Episode
import com.vroff.domain.model.streaming_available.Genre
import com.vroff.domain.model.streaming_available.HorizontalPoster
import com.vroff.domain.model.streaming_available.ImageSet
import com.vroff.domain.model.streaming_available.Season
import com.vroff.domain.model.streaming_available.Show
import com.vroff.domain.model.streaming_available.ShowType
import com.vroff.domain.model.streaming_available.VerticalPoster
import com.vroff.streamingmovie.entity.EpisodeDTO
import com.vroff.streamingmovie.entity.GenreDTO
import com.vroff.streamingmovie.entity.HorizontalPosterDTO
import com.vroff.streamingmovie.entity.ImageSetDTO
import com.vroff.streamingmovie.entity.SeasonDTO
import com.vroff.streamingmovie.entity.ShowDTO
import com.vroff.streamingmovie.entity.VerticalPosterDTO

fun ShowDTO.toShow(): Show {
    val id = this.id
    val showType = when (this.showType) {
        "series" -> ShowType.SERIES
        "movie" -> ShowType.MOVIE
        else -> {
            throw IllegalArgumentException("Item type cannot be null")
        }
    }
    val imdbId = this.imdbId
    val tmdbId = this.tmdbId
    val title = this.title
    val overview = this.overview
    val originalTitle = this.originalTitle
    val genres = this.genres.map { it.toGenre() }
    val directors = this.directors ?: emptyList()
    val cast = this.cast
    val creators = this.creators ?: emptyList()
    val seasons = this.seasons?.map { it.toSeason() }
    val imageSet = this.imageSet.toImageSet() //

    return Show(
        id = id,
        showType = showType,
        imdbId = imdbId,
        tmdbId = tmdbId,
        title = title,
        overview = overview,
        releaseYear = this.releaseYear,
        originalTitle = originalTitle,
        genres = genres,
        directors = directors,
        cast = cast,
        rating = this.rating,
        imageSet = imageSet,
        firstAirYear = this.firstAirYear,
        lastAirYear = this.lastAirYear,
        creators = creators,
        seasonCount = this.seasonCount,
        episodeCount = this.episodeCount,
        seasons = seasons,
        runtime = runtime
    )
}

private fun ImageSetDTO.toImageSet(): ImageSet {
    return ImageSet(
        verticalPoster = this.verticalPoster.toVerticalPoster(),
        horizontalPoster = this.horizontalPoster.toHorizontalPoster(),
        verticalBackdrop = this.verticalBackdrop?.toVerticalPoster(),
        horizontalBackdrop = this.horizontalBackdrop?.toHorizontalPoster(),
    )

}

private fun HorizontalPosterDTO.toHorizontalPoster(): HorizontalPoster {
    return HorizontalPoster(
        w360, w480, w720, w1080, w1440
    )
}

private fun VerticalPosterDTO.toVerticalPoster(): VerticalPoster {
    return VerticalPoster(w240, w360, w480, w600, w720)

}

private fun SeasonDTO.toSeason(): Season {
    return Season(title, firstAirYear, lastAirYear, episodes.map { it.toEpisode() })
}

private fun EpisodeDTO.toEpisode(): Episode {
    return Episode(
        title,
        overview,
        airYear,
    )
}


fun GenreDTO.toGenre(): Genre {
    val id = this.id ?: ""
    val name = this.name ?: "Unknown Genre"
    return Genre(id, name)
}