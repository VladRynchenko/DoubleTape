package com.vroff.doubletape.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.home.MainScreenContent
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.ui.R
import com.vroff.ui.ShowFormatter.formatRealiseDate
import com.vroff.ui.ui.AppAsyncImage
import com.vroff.ui.ui.BackdropImageWidget
import com.vroff.ui.ui.HeaderText
import com.vroff.ui.ui.LocalInnerPadding
import com.vroff.ui.ui.item.BackdropItem

@Composable
fun MainScreen(onItemClicked: (Int, MediaType) -> Unit) {
    val viewModule = hiltViewModel<MainViewModule>()
    val nowPlaying = viewModule.mainScreenContent.collectAsState()
    MainScreenContent(nowPlaying.value, onItemClicked)
}

@Composable
fun MainScreenContent(
    data: MainScreenContent?,
    onItemClicked: (Int, MediaType) -> Unit = { _, _ -> },
) {
    val paddings = LocalInnerPadding.current

    LazyColumn(
        contentPadding = PaddingValues(bottom = paddings.calculateBottomPadding()),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        data?.nowPlayingMovie?.first()?.let {
            item {
                BackdropImageWidget(
                    modifier = Modifier.clickable(onClick = { onItemClicked(it.id, it.mediaType) }),
                    it.backdropImage,
                ) {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }
        }

        data?.nowPlayingMovie?.let { nowPlaying ->
            item {
                Column(modifier = Modifier) {
                    HeaderText(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .padding(horizontal = 12.dp),
                        headerText = stringResource(R.string.now_playing_movie),
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        items(nowPlaying, key = { it.id }) {
                            BackdropItem(
                                modifier =
                                    Modifier
                                        .width(312.dp)
                                        .clip(RoundedCornerShape(14.dp)),
                                image = it.backdropImage,
                                title = it.title,
                                date = formatRealiseDate(it.releaseDate),
                                rating = it.voteAverage,
                                subtitle = it.genres.joinToString(SymbolConstant.MIDDLE_POINT) { genre -> genre.name },
                            ) { onItemClicked(it.id, it.mediaType) }
                        }
                    }
                }
            }
        }
        data?.popularMovie?.let { popularMovie ->
            item {
                Column(modifier = Modifier) {
                    HeaderText(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .padding(horizontal = 12.dp),
                        headerText = stringResource(R.string.popular_movie),
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        items(popularMovie, key = { it.id }) {
                            AppAsyncImage(
                                model = it.posterImage,
                                contentDescription = "Poster Image",
                                modifier =
                                    Modifier
                                        .width(112.dp)
                                        .aspectRatio(2f / 3f)
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable(onClick = { onItemClicked(it.id, it.mediaType) }),
                            )
                        }
                    }
                }
            }
        }
        data?.upcomingMovie?.let { upcomingMovie ->
            item {
                Column(modifier = Modifier) {
                    HeaderText(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                                .padding(horizontal = 12.dp),
                        headerText = stringResource(R.string.upcoming_movie),
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        items(upcomingMovie, key = { it.id }) {
                            AppAsyncImage(
                                model = it.posterImage,
                                contentDescription = "Poster Image",
                                modifier =
                                    Modifier
                                        .width(112.dp)
                                        .aspectRatio(2f / 3f)
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable(onClick = { onItemClicked(it.id, it.mediaType) }),
                            )
                        }
                    }
                }
            }
        }
    }
}
