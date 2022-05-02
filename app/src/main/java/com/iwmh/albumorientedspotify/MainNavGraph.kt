package com.iwmh.albumorientedspotify

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.view.home.HomeScreen
import com.iwmh.albumorientedspotify.view.library.LibraryScreen
import com.iwmh.albumorientedspotify.view.playlist.PlaylistScreen
import com.iwmh.albumorientedspotify.view.settings.SettingsScreen

@Composable
fun MainNavGraph(
        navController: NavHostController,
        modifier: Modifier = Modifier
){
    NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier
    ){
        composable(Screen.Home.route){
            HomeScreen(navController)
        }
        composable(Screen.Settings.route){
            SettingsScreen(navController)
        }
        composable(Screen.Library.route){
            LibraryScreen(navController)
        }
        composable(
            route = "${Screen.Playlist.route}/{" + Constants.nav_playlistId + "}",
            arguments = listOf(
                navArgument(Constants.nav_playlistId){
                    type = NavType.StringType
                }
            )
        ){ navBackStackEntry ->
            val playlistID = navBackStackEntry.arguments?.getString(Constants.nav_playlistId)
            PlaylistScreen(navController, playlistID)
        }
    }

}
