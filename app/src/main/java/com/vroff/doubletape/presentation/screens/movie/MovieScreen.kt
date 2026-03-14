package com.vroff.doubletape.presentation.screens.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vroff.domain.model.ImagePath
import com.vroff.doubletape.R
import com.vroff.ui.hoursAndMinutesUsingFormat
import com.vroff.ui.ui.ExpandableText

@Composable
fun MovieScreen(
    tmdbId: String?,
    padding: PaddingValues
) {
    val movieViewModel = hiltViewModel<MovieViewModel>()
    movieViewModel.setTMDBId(tmdbId)
    val state = movieViewModel.showState.collectAsStateWithLifecycle()
    ShowDetailsContent(state.value, padding)
}

@Composable
private fun ShowDetailsContent(
    state: MovieViewModel.ScreenState,
    padding: PaddingValues
) {
    when (state) {

        MovieViewModel.ScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MovieViewModel.ScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(state.e.toString(), color = MaterialTheme.colorScheme.error)
            }
        }

        is MovieViewModel.ScreenState.Success -> {
            val show = state.data
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = padding.calculateBottomPadding())
            ) {
                item {
                    AsyncImage(
                        ImageRequest.Builder(LocalContext.current)
                            .data(ImagePath(show.posterPath))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Movie poster",
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp),
                    ) {
                        Text(
                            text = show.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 36.sp
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {


                            Text("${show.voteAverage}/100")
                            Text(
//                                when (show.showType) {
//                                    ShowType.MOVIE ->
                                hoursAndMinutesUsingFormat(show.runtime)
//                                    ShowType.SERIES -> seasonsAndSeriesCountFormat(
//                                        show.seasonCount,
//                                        show.episodeCount
                            )

                        }
                        Text(
                            "Overview",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        ExpandableText(
                            show.overview,
                            minLines = 4
                        )
                    }
                }
                item {
                    Text(
                        "Top Cast",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    )
                    show.credits?.cast?.forEach { actor ->
                        Text(
                            actor?.name ?: "",
                            modifier = Modifier
                                .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                        )
                    }
                }
//                item {
//                    if (show.showType == ShowType.SERIES) {
//                        show.seasons?.forEachIndexed { index, season ->
//                            HideableItem(
//                                title = {
//                                    Text(stringResource(R.string.season, index + 1))
//                                },
//                                modifier = Modifier.padding(12.dp)
//                            ) {
//                                Column {
//                                    season.episodes.forEachIndexed { episodeIndex, episode ->
//                                        HideableItem(
//                                            title = {
//                                                Text(
//                                                    "Episode ${episodeIndex + 1} - ${episode.title}"
//                                                )
//                                            },
//                                            modifier = Modifier.padding(horizontal = 12.dp)
//                                        ) {
//                                            Text(
//                                                episode.airYear.toString(),
//                                            )
//                                            if (episode.overview != null) {
//                                                Text(
//                                                    episode.overview.toString()
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                }
            }
        }
    }
}