package com.vroff.ui.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import com.vroff.domain.model.Image
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.domain.model.tmdb.search.typed.MovieMediaItem
import com.vroff.domain.model.tmdb.search.typed.PersonMediaItem
import com.vroff.domain.model.tmdb.search.typed.SeriesMediaItem
import com.vroff.domain.model.tmdb.search.typed.TypedMediaItem
import com.vroff.ui.ShowFormatter.formatRealiseDate
import com.vroff.ui.preview.SearchItemPreviewProvider
import com.vroff.ui.ui.AppAsyncImage
import com.vroff.ui.ui.RatingWidget

@Preview
@Composable
fun SearchItem(
    @PreviewParameter(SearchItemPreviewProvider::class) item: TypedMediaItem,
    modifier: Modifier = Modifier,
    onItemClick: (Int, MediaType) -> Unit = { _, _ -> },
) {
    when (item) {
        is PersonMediaItem ->
            SearchBaseCard(
                image = item.image,
                title = item.name,
                subtitle = item.knownFor.joinToString(", ") { it.name ?: it.title ?: "" },
                onClick = { onItemClick(item.id, MediaType.PERSON) },
            )

        is SeriesMediaItem ->
            SearchBaseCard(
                image = item.image,
                title = item.name,
                subtitle = item.genres.joinToString(", ") { it.name },
                date = formatRealiseDate(item.firstAirDate),
                rating = item.voteAverage,
                modifier = modifier,
                onClick = { onItemClick(item.id, MediaType.SERIES) },
            )

        is MovieMediaItem ->
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

        AppAsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .width(120.dp)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(14.dp))
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
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
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (date != null && rating != null) {
                Text(
                    SymbolConstant.MIDDLE_POINT,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
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
                color = MaterialTheme.colorScheme.onSurface,
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
