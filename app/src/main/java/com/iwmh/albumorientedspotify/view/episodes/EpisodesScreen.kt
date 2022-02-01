package com.iwmh.albumorientedspotify.view.episodes

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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun EpisodesScreen(
    navController: NavController,
    showId: String?
) {
    val viewModel: EpisodesScreenViewModel = hiltViewModel()
    // Hold the Show ID for this screen.
    // TODO: Needs better code here.
    viewModel.showId = showId

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        Column {
            Text(text = "Episodes")
            Text(text = lazyPagingItems.itemCount.toString())
            LazyColumn {
                items(lazyPagingItems) { episodeItem ->
                    EpisodeCardSquare(
                        episodeName = episodeItem!!.name,
                        imageUrl = episodeItem!!.images[2].url,
                        description = episodeItem!!.description,
                        duration = episodeItem!!.duration_ms,
                        releaseDate = episodeItem!!.release_date,
                        // Navigate to episode-detail screen.
                        onClick = {
                            val encodedImageUrl
                                    = URLEncoder.encode(episodeItem!!.images[2].url, StandardCharsets.UTF_8.toString())
                            val encodedDescription
                                    = URLEncoder.encode(episodeItem!!.description, StandardCharsets.UTF_8.toString())
                            navController.navigate(
                                "${Screen.EpisodeDetail.route}/" +
                                        "${episodeItem!!.name}/" +
                                        "${encodedImageUrl}/" +
                                        "${encodedDescription}/" +
                                        "${episodeItem!!.duration_ms}/" +
                                        "${episodeItem!!.release_date}"
                            )
                        }
                    )
                }
            }
        }
    }
}
