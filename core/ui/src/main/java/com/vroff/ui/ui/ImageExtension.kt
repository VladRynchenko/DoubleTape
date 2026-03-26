package com.vroff.ui.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.vroff.domain.model.Image
import com.vroff.ui.R

@Composable
fun Image?.toRequest() =
    ImageRequest
        .Builder(LocalContext.current)
        .data(this ?: R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .crossfade(true)
        .build()
