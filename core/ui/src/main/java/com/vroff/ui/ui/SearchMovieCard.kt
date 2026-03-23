package com.vroff.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.vroff.domain.model.Image
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.typed.MovieSearchResult
import com.vroff.domain.model.tmdb.search.typed.PersonSearchResult
import com.vroff.domain.model.tmdb.search.typed.SeriesSearchResult
import com.vroff.domain.model.tmdb.search.typed.TypedSearchResult
import com.vroff.ui.ShowFormatter.formatRealiseDate
import com.vroff.ui.preview.SearchItemPreviewProvider

@Preview
@Composable
fun SearchItem(
    @PreviewParameter(SearchItemPreviewProvider::class) item: TypedSearchResult,
    modifier: Modifier = Modifier,
    onItemClick: (Int, MediaType) -> Unit = { _, _ -> },
) {
    when (item) {
        is PersonSearchResult ->
            SearchBaseCard(
                image = item.image,
                title = item.name,
                subtitle = item.knownFor.joinToString(", ") { it.name ?: it.title ?: "" },
                onClick = { onItemClick(item.id, MediaType.PERSON) },
            )

        is SeriesSearchResult ->
            SearchBaseCard(
                image = item.image,
                title = item.name,
                subtitle = item.genres.joinToString(", ") { it.name },
                date = formatRealiseDate(item.firstAirDate),
                rating = item.voteAverage,
                modifier = modifier,
                onClick = { onItemClick(item.id, MediaType.SERIES) },
            )

        is MovieSearchResult ->
            SearchBaseCard(
                image = item.image,
                title = item.title,
                subtitle = item.genres.joinToString(", ") { it.name },
                date = formatRealiseDate(item.releaseDate),
                rating = item.voteAverage,
                modifier = modifier,
                onClick = { onItemClick(item.id, MediaType.MOVIE) },
            )
    }
}

@Composable
fun SearchBaseCard(
    modifier: Modifier = Modifier,
    image: Image?,
    rating: Float? = null,
    title: String,
    subtitle: String? = null,
    date: String? = null,
    onClick: () -> Unit,
) {
    ConstraintLayout(
        modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface.copy(0.9f))
                .clickable { onClick() },
    ) {
        val (poster, titleRef, subtitleRef, dateRef) = createRefs()

        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .size(120.dp, 160.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier =
                Modifier.constrainAs(titleRef) {
                    start.linkTo(poster.end, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    top.linkTo(poster.top, 8.dp)
                    width = Dimension.fillToConstraints
                },
        )

        Row(
            modifier =
                Modifier.constrainAs(dateRef) {
                    start.linkTo(titleRef.start)
                    top.linkTo(titleRef.bottom, 4.dp)
                },
        ) {
            date?.takeIf { it != SymbolConstant.EMPTY }?.let { date1 ->
                Text(
                    text = date1,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            if (date != null && rating != null) {
                Text(
                    SymbolConstant.MIDDLE_POINT,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            rating?.let {
                RatingWidget(rating)
            }
        }

        subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                modifier =
                    Modifier.constrainAs(subtitleRef) {
                        start.linkTo(titleRef.start)
                        end.linkTo(titleRef.end)
                        width = Dimension.fillToConstraints
                        if (!date.isNullOrEmpty() || rating != null) {
                            top.linkTo(dateRef.bottom)
                        } else {
                            top.linkTo(titleRef.bottom, 4.dp)
                        }
                    },
            )
        }
    }
}
