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
            Text(text = "Your library.")
            LazyColumn {
                items(lazyPagingItems) { item ->
                    ShowCardSquare(
                        show = item?.show,
                    ){  // onClick
                        // Save "showId" for EpisodesScreen to use.
                        viewModel.saveShowId(item?.show?.id)
                        // Navigate to EpisodesScreen.
                        navController.navigate("${Screen.Episodes.route}/${item?.show?.id}")
                    }
                }
            }
        }
    }






}
