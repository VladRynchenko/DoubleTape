package com.vroff.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vroff.domain.model.Image
import com.vroff.ui.R

@Composable
fun Image?.toRequest(): ImageRequest {
    val context = LocalContext.current
    return remember(this, context) {
        ImageRequest
            .Builder(context)
            .data(this)
            .crossfade(true)
            .build()
    }
}

@Composable
fun ImagePlaceholder() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.gallery_slash),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.LightGray,
        )
    }
}
