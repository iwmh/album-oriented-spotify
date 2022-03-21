package com.iwmh.albumorientedspotify.view.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.iwmh.albumorientedspotify.Screen

@Composable
fun LibraryScreen(navController: NavController) {
    val viewModel: LibraryScreenViewModel = hiltViewModel()

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        Column {
            Text(text = "Playlists you're following")
            LazyColumn {
                items(lazyPagingItems) { item ->
                    PlaylistCardSquare(
                        playlist = item,
                    ){  // onClick
                        // Save "playlistId" for PlaylistScreen to use.
                        viewModel.savePlaylistId(item?.id)
                        // Navigate to PlaylistScreen.
                        navController.navigate("${Screen.Playlist.route}/${item?.id}")
                    }
                }
            }
        }
    }






}
