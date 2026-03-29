package com.vroff.ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vroff.domain.model.BackdropImage

@Composable
fun BackdropImageWidget(
    modifier: Modifier = Modifier,
    image: BackdropImage?,
    contentDescription: String? = null,
    content: @Composable () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier =
            modifier
                .aspectRatio(16f / 9f),
    ) {
        AppAsyncImage(
            model = image.toRequest(),
            contentDescription = contentDescription ?: "Backdrop Image",
            modifier =
                Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .aspectRatio(16f / 9f),
        )
        Box(
            modifier =
                Modifier
                    .drawBehind {
                        drawRect(
                            brush =
                                Brush.verticalGradient(
                                    colors =
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.85f),
                                        ),
                                    startY = 0f,
                                    endY = size.height,
                                ),
                        )
                    }.fillMaxWidth(),
        ) {
            content()
        }
    }
}
