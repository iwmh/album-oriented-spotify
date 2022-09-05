package com.iwmh.albumorientedspotify.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
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

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        Column {
            Text(text = "■ Your own playlists.")
            Text(text = "Toggle switches. Playlists toggled here will be shown inside the playlist view, " +
                    "and let you add their all tracks to the playlists easily.")
            LazyColumn {
                items(lazyPagingItems) { item ->
                    SettingsPlaylistCardSquare(
                        playlist = item,
                        selectedPlaylistIDs = uiState.value.enabledPlaylistIDs
                    )
                }
            }
        }
    }
}