package com.iwmh.albumorientedspotify.view.playlist

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

@Composable
fun PlaylistScreen(
    navController: NavController,
    playlistID: String?
) {
    val viewModel: PlaylistScreenViewModel = hiltViewModel()
    // Hold the Playlist ID for this screen.
    // TODO: Needs better code here.
    viewModel.playlistID = playlistID

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        Column {
            Text(text = "Albums.")
            Text(text = lazyPagingItems.itemCount.toString())
            LazyColumn {
                items(lazyPagingItems) { trackItem->
                    AlbumCardSquare(
                        albumID = trackItem?.track?.album?.id,
                        albumName = trackItem?.track?.album?.name,
                        imageUrl = trackItem?.track?.album!!.images[2].url,
                        artists = trackItem?.track?.album!!.artists,
                        releaseDate = trackItem?.track?.album!!.release_date,
                        // Navigate to episode-detail screen.
                        /*
                        onClick = {
                            val encodedImageUrl
                                    = URLEncoder.encode(trackItem!!.images[2].url, StandardCharsets.UTF_8.toString())
                            val encodedDescription
                                    = URLEncoder.encode(trackItem!!.description, StandardCharsets.UTF_8.toString())
                            navController.navigate(
                                "${Screen.EpisodeDetail.route}/" +
                                        "${trackItem!!.name}/" +
                                        "${encodedImageUrl}/" +
                                        "${encodedDescription}/" +
                                        "${trackItem!!.duration_ms}/" +
                                        "${trackItem!!.release_date}"
                            )
                        }
                         */
                    )
                }
            }
        }
    }
}
