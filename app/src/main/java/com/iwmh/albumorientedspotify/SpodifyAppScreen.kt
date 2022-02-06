package com.iwmh.albumorientedspotify

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iwmh.albumorientedspotify.ui.theme.AlbumorientedspotifyTheme

sealed class Screen(val route: String, val name: String, val iconVector: ImageVector?) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Library : Screen("library", "Library", Icons.Default.List)
    object Playlist: Screen("playlist", "Playlist", null)
    object EpisodeDetail: Screen("episodeDetail", "EpisodeDetail", null)
}

@Composable
fun AlbumOrientedSpotifyAppScreen(name: String, viewModel: MainViewModel){
    AlbumorientedspotifyTheme {
        val items = listOf(
            Screen.Home,
            Screen.Search,
            Screen.Library
        )
        // NavController
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach{ screen ->
                        BottomNavigationItem(
                            icon = {Icon(screen.iconVector!!, contentDescription = null)},
                            label = {Text(screen.name)},
                            selected = currentDestination?.hierarchy?.any{it.route == screen.route} == true,
                            onClick = {
                                navController.navigate(screen.route){
                                    /***
                                     * TODO: Reluctant to activate this. This prevents the app to clear the viewmodel when reaching the the nav destination.
                                     * Not the Spotify official app's way it looks, but this is just a workaround to keep viewmodel to uncleared.
                                     */
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }

                }

            }
        ) { innerPadding ->
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                MainNavGraph(
                    navController = navController,
                    Modifier.padding(innerPadding)
                )
            }
        }
    }
}

