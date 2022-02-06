package com.iwmh.albumorientedspotify

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.view.episodedetail.EpisodeDetailScreen
import com.iwmh.albumorientedspotify.view.home.HomeScreen
import com.iwmh.albumorientedspotify.view.library.LibraryScreen
import com.iwmh.albumorientedspotify.view.playlist.PlaylistScreen
import com.iwmh.albumorientedspotify.view.search.SearchScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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
            HomeScreen(name = "Hiroshi")
        }
        composable(Screen.Search.route){
            SearchScreen(name = "Hiroshi")
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
        composable(
            route = "${Screen.EpisodeDetail.route}/" +
                    "{" + Constants.nav_episodeName + "}/" +
                    "{" + Constants.nav_imageUrl + "}/" +
                    "{" + Constants.nav_description  + "}/" +
                    "{" + Constants.nav_duration + "}/" +
                    "{" + Constants.nav_releaseDate + "}",
            arguments = listOf(
                navArgument(Constants.nav_episodeName){
                    type = NavType.StringType
                },
                navArgument(Constants.nav_imageUrl){
                    type = NavType.StringType
                },
                navArgument(Constants.nav_description){
                    type = NavType.StringType
                },
                navArgument(Constants.nav_duration){
                    type = NavType.IntType
                },
                navArgument(Constants.nav_releaseDate){
                    type = NavType.StringType
                },
            )
        ){ navBackStackEntry ->
            val episodeName = navBackStackEntry.arguments?.getString(Constants.nav_episodeName)
            val imageUrl = navBackStackEntry.arguments?.getString(Constants.nav_imageUrl)
            val decodedImageUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString())
            val description = navBackStackEntry.arguments?.getString(Constants.nav_description)
            val decodedDescription = URLDecoder.decode(description, StandardCharsets.UTF_8.toString())
            val duration = navBackStackEntry.arguments?.getInt(Constants.nav_duration)
            val releaseDate = navBackStackEntry.arguments?.getString(Constants.nav_releaseDate)
            EpisodeDetailScreen(
                episodeName = episodeName!!,
                imageUrl = decodedImageUrl,
                description = decodedDescription,
                duration = duration!!,
                releaseDate = releaseDate!!
            )
        }
    }

}
