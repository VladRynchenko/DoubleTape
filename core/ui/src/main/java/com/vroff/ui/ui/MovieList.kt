package com.vroff.ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.streamingavailable.Show

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    showList: List<Show>? = listOf(),
    onItemClick: (String) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (showList == null) {
            items(4) {
                EmptyMovieCard()
            }
        } else {
            items(showList) { item ->
                MovieCard(
                    show = item,
                    onClick = onItemClick,
                )
            }
        }
    }
}
