package com.vroff.ui.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.tmdb.common.VideoData
import com.vroff.ui.theme.MovieDDTheme
import com.vroff.ui.ui.AppAsyncImage
import com.vroff.ui.ui.widget.Quality
import com.vroff.ui.ui.widget.QualityWidget

@Composable
fun VideoItem(
    videoData: VideoData,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(end = 8.dp)
            .clickable(
                onClick = {
                    uriHandler.openUri("https://www.youtube.com/watch?v=${videoData.key}")
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppAsyncImage(
            model =
                if (videoData.site ==
                    "YouTube"
                ) {
                    "https://img.youtube.com/vi/${videoData.key}/hqdefault.jpg"
                } else {
                    null
                },
            contentDescription = "${videoData.type} Thumbnail",
            modifier =
                Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .aspectRatio(16f / 9f),
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
        ) {
            Text(
                videoData.name,
                maxLines = 3,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                videoData.type,
                style = MaterialTheme.typography.labelMedium,
            )
            QualityWidget(videoData.size.mapToQuality())
        }
    }
}

private fun Int.mapToQuality(): Quality =
    when (this) {
        in 0..360 -> Quality.SD
        in 361..720 -> Quality.HD
        in 721..1080 -> Quality.FHD
        in 1081..2160 -> Quality.UHD
        else -> Quality.QHD
    }

@Preview
@Composable
fun VideoItemPreview() {
    val item =
        VideoData(
            iso6391 = "en",
            iso31661 = "US",
            name = "Official Trailer",
            key = "nE11U5iBnH0",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
            official = true,
            publishedAt = "2024-04-10T18:00:32.000Z",
            id = "66358c9b0f52650125bb114c",
        )
    MovieDDTheme {
        VideoItem(
            item,
            modifier =
                Modifier
                    .height(100.dp),
        )
    }
}
