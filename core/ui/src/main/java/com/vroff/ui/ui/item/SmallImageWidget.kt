package com.vroff.ui.ui.item

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.vroff.domain.model.Image
import com.vroff.ui.ui.ShimmerPlaceHolder
import com.vroff.ui.ui.toRequest

@Composable
fun SmallImageWidget(
    modifier: Modifier = Modifier,
    model: Image?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
) {
    SubcomposeAsyncImage(
        model = model.toRequest(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = { ShimmerPlaceHolder(modifier.matchParentSize()) },
        modifier =
            modifier
                .width(120.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(14.dp)),
    )
}
