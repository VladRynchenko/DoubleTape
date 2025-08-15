package com.vroff.moviedd.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import com.vroff.moviedd.domain.models.Show

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    showList: List<Show>? = listOf(),
    imageLoader: ImageLoader,
    onItemClick: (String) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showList == null) {
            items(4) {
                EmptyMovieCard()
            }
        } else {
            items(showList) { item ->
                MovieCard(
                    show = item,
                    imageLoader = imageLoader,
                    onClick = onItemClick
                )
            }
        }
    }
}
