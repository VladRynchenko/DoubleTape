package com.vroff.doubletape.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.vroff.domain.model.streamingavailable.ShowState
import com.vroff.ui.ui.MovieList

// @Preview(showSystemUi = true, showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WelcomeScreen() {
    val viewModel = hiltViewModel<WelcomeViewModel>()
    when (val result = viewModel.showState) {
        is ShowState.Success -> {
            MovieList(
                showList = result.showList,
                onItemClick = { id ->
                },
            )

            Button(
                onClick = { viewModel.getShows() },
            ) {
                Text("Update")
            }
        }

        is ShowState.Error -> TODO()
        ShowState.Loading -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
//                    CircularProgressIndicator()
//                Text("Welcome")
            }
        }

        ShowState.Waiting -> {}
    }
}
