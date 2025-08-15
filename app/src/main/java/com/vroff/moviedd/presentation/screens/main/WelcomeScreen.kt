package com.vroff.moviedd.presentation.screens.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Density
import androidx.hilt.navigation.compose.hiltViewModel
import com.vroff.moviedd.domain.ShowState
import com.vroff.moviedd.presentation.ui.MovieList


//@Preview(showSystemUi = true, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WelcomeScreen() {
    val viewModel = hiltViewModel<WelcomeViewModel>()
    when (val result = viewModel.showState) {
        is ShowState.Success -> {
            MovieList(
                showList = result.showList,
                imageLoader = viewModel.imageLoader,
                onItemClick = { id ->

                }
            )

            Button(
                onClick = { viewModel.getShows() }
            ) {
                Text("Update")
            }
        }

        is ShowState.Error -> TODO()
        ShowState.Loading -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
//                    CircularProgressIndicator()
//                Text("Welcome")
            }
        }

        ShowState.Waiting -> {}
    }
}
