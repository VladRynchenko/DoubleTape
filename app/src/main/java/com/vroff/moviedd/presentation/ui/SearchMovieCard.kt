package com.vroff.moviedd.presentation.ui

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.vroff.moviedd.domain.models.Show
import com.vroff.moviedd.presentation.screens.movie.ShowPreviewParameterProvider

@Preview
@Composable
fun SearchMovieCard(
    @PreviewParameter(ShowPreviewParameterProvider::class) item: Show,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
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
                item.imdbId.let { onItemClick.invoke(it) }
            }
    ) {
        val (poster, title, genres, year, overview) = remember { createRefs() }
        AsyncImage(
            model = item.imageSet.verticalPoster.w360,
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
        if (item.genres.isNotEmpty())
            Text(
                item.genres.joinToString(", ") { it.name },
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
            item.getReleaseData(),
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
        Text(
            item.overview,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(overview) {
                    start.linkTo(title.start)
                    end.linkTo(title.end)
                    top.linkTo(year.bottom)
                    width = Dimension.fillToConstraints
                }
        )
    }
}