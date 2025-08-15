package com.vroff.moviedd

import NavigationState
import ShowTopAppBar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.vroff.moviedd.presentation.screens.Graph
import com.vroff.moviedd.presentation.screens.MainGraph
import com.vroff.moviedd.presentation.screens.main.WelcomeScreen
import com.vroff.moviedd.presentation.screens.movie.MovieScreen
import com.vroff.moviedd.presentation.screens.search.SearchScreen
import com.vroff.moviedd.ui.theme.MovieDDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val mainViewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            val context = LocalContext.current
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: Graph.Main.route
            val query = mainViewModel.query.collectAsState()

            MovieDDTheme {
                Scaffold(
                    topBar = {
                        ShowTopAppBar(
                            searchQuery = query.value,
                            onSearchQueryChange = mainViewModel::setQuery,
                            onActionIconClick = {
                                when (currentRoute) {
                                    Graph.Main.route -> {
                                        mainViewModel.clearQuery()
                                        navController.navigate(Graph.Search.route)
                                    }

                                    else -> NavigationState.More
                                }
                            },
                            onNavigationIconClick = {
                                when (navController.currentDestination?.route) {
                                    Graph.Main.route -> {
                                        Toast.makeText(context, "MAIN", Toast.LENGTH_SHORT).show()
                                    }

                                    Graph.Search.route, Graph.Details.route -> navController.popBackStack()
                                    else -> NavigationState.More
                                }
                            },
                            state = when (currentRoute) {
                                Graph.Main.route -> NavigationState.MainScreen
                                Graph.Details.route -> NavigationState.More
                                Graph.Search.route -> NavigationState.Search
                                else -> NavigationState.More
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Graph.route,
                    ) {
                        navigation(Graph.Main.route, route = Graph.route) {
                            composable(Graph.Main.route) { WelcomeScreen() }
                            composable(Graph.Search.route) {
                                SearchScreen(
                                    query.value.text,
                                    innerPadding,
                                ) { id ->
                                    navController.navigate("${Graph.route}/${Graph.Details.route}/$id")
                                }
                            }
                            composable(
                                route = Graph.Details.routeWithArgs,
                                arguments = Graph.Details.arguments,
                            ) { backStackEntry ->
                                val id =
                                    backStackEntry.arguments?.getString(Graph.Details.idTypeArg)
                                MovieScreen(id, innerPadding)
                            }
                        }
                    }

                }
//                    SearchScreen(searchQuery, modifier = Modifier.padding(innerPadding))


            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }



