package com.vroff.ui.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.Image
import com.vroff.domain.model.tmdb.common.Cast
import com.vroff.domain.model.tmdb.common.CastBase
import com.vroff.domain.model.tmdb.common.SeriesCast
import com.vroff.ui.R
import com.vroff.ui.preview.CastItemPreviewProvider
import com.vroff.ui.ui.AppAsyncImage
import com.vroff.ui.ui.toRequest

@Composable
fun CastWidget(
    modifier: Modifier = Modifier,
    cast: List<CastBase> = listOf(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(10.dp),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemClick: (Int) -> Unit = {},
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        contentPadding = contentPadding,
    ) {
        items(cast) {
            CastItem(cast = it, onItemClick = onItemClick)
        }
    }
}

@Preview
@Composable
fun CastItem(
    @PreviewParameter(CastItemPreviewProvider::class) cast: CastBase,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
) {
    when (cast) {
        is Cast ->
            CastBaseItem(
                onClick = { onItemClick(cast.id) },
                modifier = modifier,
                image = cast.profileImage,
                name = cast.name,
                role = cast.character,
            )

        is SeriesCast ->
            CastBaseItem(
                onClick = { onItemClick(cast.id) },
                modifier = modifier,
                image = cast.profileImage,
                name = cast.name,
                role = cast.roles?.firstOrNull()?.character,
                episodesCount = cast.totalEpisodeCount,
            )
    }
}

@Composable
fun CastBaseItem(
    modifier: Modifier = Modifier,
    image: Image?,
    name: String,
    role: String?,
    episodesCount: Int? = null,
    onClick: () -> Unit,
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val density = LocalDensity.current
    val cornerRadius = with(density) { 12.dp.toPx() }
    val imageHeight = 160.dp

    Card(
        onClick = onClick,
        modifier =
            modifier
                .width(120.dp)
                .drawBehind {
                    val imageHeightPx = (imageHeight / 2).toPx()
                    drawRoundRect(
                        color = surfaceColor,
                        topLeft = Offset(0f, imageHeightPx),
                        size = Size(size.width, size.height - imageHeightPx),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    )
                },
        colors =
            CardDefaults.cardColors().copy(
                containerColor = Color.Transparent,
            ),
    ) {
        val containerHeight = if (episodesCount != null) 68.dp else 48.dp

        AppAsyncImage(
            model = image.toRequest(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .size(120.dp, 160.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
                    .clip(RoundedCornerShape(14.dp)),
        )
        Column(
            modifier =
                Modifier
                    .padding(8.dp)
                    .height(containerHeight),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = role.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
            )
            episodesCount?.let {
                Text(
                    text = pluralStringResource(R.plurals.count_episodes, it, it),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
