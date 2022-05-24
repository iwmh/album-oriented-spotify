package com.iwmh.albumorientedspotify.view.playlist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    /* ※Changed to get playlist id inside ViewModel by savedStateHandle.get(Constants.nav_playlistId).
    // Hold the Playlist ID for this screen.
    viewModel.playlistID = playlistID
    */
    val context = LocalContext.current

    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    val isRefreshing by viewModel.isRefreshing.collectAsState()

    // for playlists to show under the playlist card.
    val playlistIDList = viewModel.playlistIDList

    // playlist id and name list.
    val playlistIDAndNameList = viewModel.playlistIDAndName

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            lazyPagingItems.refresh()
        }
    ) {
        Column {
            Text(text = "...showing only albums in the playlist.")
            LazyColumn {
                items(lazyPagingItems) { trackItem->
                    AlbumCardSquare(
                        context = context,
                        albumID = trackItem?.track?.album?.id,
                        albumName = trackItem?.track?.album?.name,
                        totalTracksInAlbum = trackItem?.track?.album?.total_tracks,
                        imageUrl = trackItem?.track?.album?.images?.get(2)?.url,
                        artists = trackItem?.track?.album?.artists?.map { it.name },
                        releaseDate = trackItem?.track?.album?.release_date,
                        playlistIDList = playlistIDList,
                        playlistIDAndNameList = playlistIDAndNameList,
                    )
                }
            }
        }
        // Loading Indicator
        if(viewModel.addingTracksToPlaylist){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88FFFFFF)),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()  // 丸い進捗インジゲータを中央に表示する
            }
        }
        // Toast Widget for error message.
        if(viewModel.toastMessage != ""){
            Toast.makeText(context, viewModel.toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
