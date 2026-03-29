package com.vroff.ui.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vroff.ui.theme.Gold
import com.vroff.ui.theme.White

enum class Quality {
    SD,
    HD,
    FHD,
    UHD,
    QHD,
}

@Composable
fun QualityWidget(
    quality: Quality,
    modifier: Modifier = Modifier,
) {
    val label =
        remember(quality) {
            when (quality) {
                Quality.SD -> "SD"
                Quality.HD -> "HD"
                Quality.FHD -> "FHD"
                Quality.UHD -> "UHD"
                Quality.QHD -> "4K"
            }
        }

    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Gold)
                .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = White,
        )
    }
}

@Preview
@Composable
fun QualityPreview() {
    QualityWidget(Quality.SD)
}
