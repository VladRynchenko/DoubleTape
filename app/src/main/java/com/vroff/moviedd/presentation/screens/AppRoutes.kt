package com.vroff.moviedd.presentation.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

data class MainGraph(override val route: String) : GraphDestination {
    data object SearchRoute : ShowDestination {
        override val route: String
            get() = "search"
    }

    object MainScreenRoute : ShowDestination {
        override val route: String
            get() = "main-screen"
    }
}

@Serializable
data object Graph : ShowDestination {
    override val route = "graph"

    @Serializable
    data object Search: ShowDestination{
        override val route = "search"
    }

    @Serializable
    data object Main : ShowDestination {
        override val route = "main-screen"
    }

    @Serializable
    data object Details: ShowDestination{
        override val route = "details"
        const val idTypeArg = "tmdbIdArg"
        val routeWithArgs = "${Graph.route}/${route}/{${idTypeArg}}"
        val arguments = listOf(
            navArgument(idTypeArg) { type = NavType.StringType }
        )
    }
}

interface ShowDestination {
    val route: String
}

interface GraphDestination {
    val route: String
}


