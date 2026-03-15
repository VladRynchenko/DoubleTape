package com.vroff.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.typed_result.MovieSearchResult
import com.vroff.domain.model.tmdb.search.typed_result.PersonSearchResult
import com.vroff.domain.model.tmdb.search.typed_result.SerialSearchResult
import com.vroff.domain.model.tmdb.search.typed_result.TypedSearchResult

@Composable
fun SearchItem(
    item: TypedSearchResult,
    modifier: Modifier = Modifier,
    onItemClick: (Int, MediaType) -> Unit = { id, type -> },
) {
    when (item) {
        is MovieSearchResult -> SearchMovieCard(item, modifier, onItemClick)
        is PersonSearchResult -> SearchPersonCard(item, modifier, onItemClick)
        is SerialSearchResult -> SearchSerialCard(item, modifier, onItemClick)
    }
}

@Composable
fun SearchSerialCard(
    item: SerialSearchResult,
    modifier: Modifier,
    onItemClick: (Int, MediaType) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(12.dp)
            )
            .clickable {
                onItemClick(item.id, MediaType.SERIES)
            }
    ) {
        val (poster, title, genres, year, overview) = remember { createRefs() }
        AsyncImage(
            model = item.posterImage,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(140.dp, 210.dp)
                .background(MaterialTheme.colorScheme.onSurface)
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )
        Text(
            item.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(poster.top, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    start.linkTo(poster.end, 12.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            item.genreIds.joinToString(", ") { it.toString() },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(genres) {
                    top.linkTo(title.bottom)
                    end.linkTo(title.end)
                    start.linkTo(title.start)
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            item.firstAirDate,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(year) {
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    top.linkTo(genres.bottom)
                    horizontalBias = 0f
                }
        )
    }
}

@Composable
fun SearchPersonCard(
    item: PersonSearchResult,
    modifier: Modifier,
    onItemClick: (Int, MediaType) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(12.dp)
            )
            .clickable {
                onItemClick(item.id, MediaType.MOVIE)
            }
    ) {
        val (poster, title, genres, year, overview) = remember { createRefs() }
        AsyncImage(
            model = item.profileImage,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(140.dp, 210.dp)
                .background(MaterialTheme.colorScheme.onSurface)
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )
        Text(
            item.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(poster.top, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    start.linkTo(poster.end, 12.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            item.gender.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(genres) {
                    top.linkTo(title.bottom)
                    end.linkTo(title.end)
                    start.linkTo(title.start)
                    width = Dimension.fillToConstraints
                }
        )
    }
}

@Composable
fun SearchMovieCard(
    item: MovieSearchResult,
    modifier: Modifier = Modifier,
    onItemClick: (Int, MediaType) -> Unit = { id, type -> },
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(12.dp)
            )
            .clickable {
                onItemClick(item.id, MediaType.MOVIE)
            }
    ) {
        val (poster, title, genres, year, overview) = remember { createRefs() }
        AsyncImage(
            model = item.posterImage,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(140.dp, 210.dp)
                .background(MaterialTheme.colorScheme.onSurface)
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )
        Text(
            item.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(poster.top, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    start.linkTo(poster.end, 12.dp)
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            item.genreIds.joinToString(", ") { it.toString() },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(genres) {
                    top.linkTo(title.bottom)
                    end.linkTo(title.end)
                    start.linkTo(title.start)
                    width = Dimension.fillToConstraints
                }
        )
        Text(
            item.releaseDate,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .constrainAs(year) {
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    top.linkTo(genres.bottom)
                    horizontalBias = 0f
                }
        )
    }
}