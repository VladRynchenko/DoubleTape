package com.vroff.doubletape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vroff.domain.model.tmdb.search.MediaType
import com.vroff.doubletape.detail.movie.MovieScreen
import com.vroff.doubletape.detail.profile.ProfileScreen
import com.vroff.doubletape.presentation.screens.Graph
import com.vroff.doubletape.presentation.screens.main.WelcomeScreen
import com.vroff.search.SearchScreen
import com.vroff.ui.theme.MovieDDTheme
import com.vroff.ui.ui.ShowTopAppBar
import com.vroff.ui.ui.TopBarState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val destination = navBackStackEntry?.destination

            val query by mainViewModel.query.collectAsState()

            var topBarState by remember { mutableStateOf<TopBarState>(TopBarState.Default) }

            LaunchedEffect(destination) {
                if (destination != null) {
                    topBarState = destination.toTopBarState()
                }
            }

            MovieDDTheme {
                Scaffold(
                    topBar = {
                        ShowTopAppBar(
                            query = query,
                            onActionIconClick = {
                                if (topBarState == TopBarState.Default) {
                                    navController.navigate(Graph.Search)
                                }
                            },
                            onNavigationIconClick = {
                                when {
                                    topBarState == TopBarState.Default -> {}
                                    else -> navController.popBackStack()
                                }
                            },
                            state = topBarState,
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Graph.Search,
                    ) {
                        composable<Graph.Main> {
                            WelcomeScreen()
                        }

                        composable<Graph.Search> {
                            SearchScreen(
                                searchQuery = query.text.toString(),
                                padding = innerPadding,
                                onItemClick = { id, type ->
                                    if (type.isShow) {
                                        navController.navigate(Graph.Details(id, type))
                                    } else if (type == MediaType.PERSON) {
                                        navController.navigate(Graph.Profile(id))
                                    }
                                },
                            )
                        }

                        composable<Graph.Details> { backStackEntry ->
                            val details = backStackEntry.toRoute<Graph.Details>()
                            MovieScreen(
                                tmdbId = details.id,
                                type = details.type,
                                padding = innerPadding,
                                onCastItemClick = {
                                    navController.navigate(Graph.Profile(it))
                                },
                            )
                        }
                        composable<Graph.Profile> { backStackEntry ->
                            val details = backStackEntry.toRoute<Graph.Profile>()
                            ProfileScreen(
                                profileId = details.id,
                                padding = innerPadding,
                                onShowClick = { id, type ->
                                    navController.navigate(Graph.Details(id, type))
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

fun <T : Any> NavHostController.navigateSingleTopTo(route: T) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id,
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavDestination?.toTopBarState(): TopBarState =
    when {
        this?.hasRoute<Graph.Main>() == true -> TopBarState.Default
        this?.hasRoute<Graph.Search>() == true -> TopBarState.Search
        this != null -> TopBarState.More
        else -> TopBarState.Default
    }
