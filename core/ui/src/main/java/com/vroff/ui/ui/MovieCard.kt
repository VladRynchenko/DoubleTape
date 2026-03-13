package com.vroff.ui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.vroff.domain.models.Show

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    show: Show? = null,
    imageLoader: ImageLoader,
    onClick: ((String) -> Unit)
) {
    show?.let {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .size(140.dp, 210.dp)
                .clickable { show.imdbId.let { id -> onClick.invoke(id) } },
        ) {
            AsyncImage(
                model = show.imageSet.verticalPoster.w360,
                contentDescription = "Movie poster",
                contentScale = ContentScale.Crop,
                imageLoader = imageLoader,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun EmptyMovieCard(
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .size(140.dp, 210.dp)
    ) { }
}