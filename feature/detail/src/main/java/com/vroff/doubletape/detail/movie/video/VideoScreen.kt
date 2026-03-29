package com.vroff.doubletape.detail.movie.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.constants.SymbolConstant
import com.vroff.domain.model.tmdb.common.VideoData
import com.vroff.doubletape.detail.R
import com.vroff.ui.ui.ErrorScreen
import com.vroff.ui.ui.LocalInnerPadding
import com.vroff.ui.ui.item.VideoItem

@Composable
fun VideoScreen(videos: List<VideoData>) {
    if (videos.isEmpty()) {
        ErrorScreen(
            errorText = stringResource(R.string.no_videos_found),
            colorText = MaterialTheme.colorScheme.onSurface,
        )
        return
    }
    VideoContent(videos)
}

@Composable
fun VideoContent(listVideo: List<VideoData>) {
    val padding = LocalInnerPadding.current

    val officialText = stringResource(R.string.official)
    val sortedList =
        remember(listVideo) {
            val sorted =
                listVideo
                    .sortedWith(
                        compareByDescending<VideoData> { it.official }
                            .thenByDescending { it.type },
                    )

            sorted
                .groupBy {
                    val officialPrefix = if (it.official) "$officialText " else SymbolConstant.EMPTY
                    officialPrefix + it.type
                }.map { it.key to it.value }
        }

    LazyColumn(
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier =
            Modifier
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
    ) {
        sortedList.forEach { (string, data) ->

            item {
                Text(string, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            }

            items(data) {
                VideoItem(it, Modifier.height(120.dp))
            }
        }
    }
}
