package com.vroff.doubletape.detail.movie

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.vroff.doubletape.detail.Credits
import com.vroff.doubletape.detail.Details
import com.vroff.doubletape.detail.DetailsGraph
import com.vroff.doubletape.detail.Profile
import com.vroff.doubletape.detail.Video
import com.vroff.doubletape.detail.movie.credits.FullCreditsScreen
import com.vroff.doubletape.detail.movie.video.VideoScreen

fun NavGraphBuilder.movieGraph(navController: NavController) {
    navigation<DetailsGraph>(startDestination = Details::class) {
        composable<Details> { backStackEntry ->
            val details = backStackEntry.toRoute<Details>()
            MovieScreen(
                tmdbId = details.id,
                type = details.type,
                onCastItemClick = {
                    navController.navigate(Profile(it))
                },
                onVideoClick = {
                    navController.navigate(Video)
                },
                onFullCreditsClick = {
                    navController.navigate(Credits)
                },
            )
        }
        composable<Video> { backStackEntry ->

            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Details::class)
                }

            val viewModel: MovieViewModel =
                hiltViewModel(parentEntry)

            val videos by viewModel.videoStateFlow.collectAsState()

            VideoScreen(videos)
        }

        composable<Credits> { backStackEntry ->
            val parentEntry =
                remember(backStackEntry) {
                    navController.getBackStackEntry(Details::class)
                }
            val viewModel: MovieViewModel =
                hiltViewModel(parentEntry)

            val credits by viewModel.creditsStateFlow.collectAsState()

            FullCreditsScreen(
                credits,
                onPersonItemClick = {
                    navController.navigate(Profile(it))
                },
            )
        }
    }
}
