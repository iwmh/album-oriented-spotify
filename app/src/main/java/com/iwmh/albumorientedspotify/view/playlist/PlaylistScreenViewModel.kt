package com.iwmh.albumorientedspotify.view.playlist

import android.os.Handler
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
import com.iwmh.albumorientedspotify.repository.model.api.Track
import com.iwmh.albumorientedspotify.repository.model.api.TrackItem
import com.iwmh.albumorientedspotify.repository.pagingsource.PlaylistScreenPagingSource
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var toastMessage by mutableStateOf("")

    // PlaylistID for this page.
    var playlistID: String? = ""

    // Playlist IDs to show below the playlist card.
    var playlistIDList: List<String> = emptyList()

    // Pair of PlaylistID and Playlist Name to be used to restore playlist name from id.
    val playlistIDAndName by mutableStateOf(mutableStateListOf<Playlist>())

    // adding tracks to playlist.
    var addingTracksToPlaylist by mutableStateOf(false)

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        PlaylistScreenPagingSource(
//            savedStateHandle.get(Constants.nav_playlistId),
            playlistID,
            remoteDataSource,
            injectableConstants
        )
    }.flow.cachedIn(viewModelScope)

    init {
        try {

            viewModelScope.launch {

                // Hold playlistID inside ViewModel.
                playlistID = savedStateHandle.get(Constants.nav_playlistId)

                // Get playlist IDs stored in the SharedPreferences.
                var playlistIDsString = remoteDataSource.readData(Constants.selected_playlist_ids_comma_separated)
                if (playlistIDsString != null) {
                    playlistIDList = playlistIDsString.split(',')
                }

                // Get playlist's name...
                val deferredList = playlistIDList.map{ playlistID -> remoteDataSource.getPlaylistAsync(playlistID)}
                val playlistIDAndNameTmp = deferredList.awaitAll()
                playlistIDAndNameTmp.forEach{
                    playlistIDAndName.add(it)
                }
            }

        } catch (e: Exception){
            print(e.toString())
        }
    }

    /*
     return null → successful response.
     return string → erro message.
     */
    fun addAllTracksInAlbumToPlaylist(albumID: String, playlistID: String, playlistName: String) {

        // List of TrackItem in album (NOT 'Track')
        var trackItemListInAlbum = mutableListOf<Track>()

        // start adding.....
        addingTracksToPlaylist = true

        viewModelScope.launch {
            try {

                var nextUrl: String? = null

                do{
                    var result = remoteDataSource.getAlbumItems(albumID = albumID, url = nextUrl)
                    nextUrl = result.next
                    trackItemListInAlbum.addAll(result.items)

                } while(nextUrl != null)

                // extract Spotify URIs
                var trackURIList = trackItemListInAlbum.map { it.uri }

                // add tracks to the playlist.
                remoteDataSource.addItemsToPlaylist(playlistID, trackURIList)

                toastMessage = "Added to $playlistName"

            } catch (e: Exception){
                // eはログに出力
                toastMessage = "Adding failed. Please try agein."
            } finally {
                // end adding...
                addingTracksToPlaylist = false
                //toastMessage = ""
            }
        }
    }
}

