package com.iwmh.albumorientedspotify.view.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.iwmh.albumorientedspotify.repository.model.api.Playlist

@Composable
fun SettingsPlaylistCardSquare(
    playlist: Playlist?,
    selectedPlaylistIDs: List<String>,
){
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    Row(
    ) {
        Text(text = playlist!!.name)
        Switch(
            checked = selectedPlaylistIDs.contains(playlist.id),
            onCheckedChange = { checked ->
                val playlistIDs = selectedPlaylistIDs.toMutableList()
                if(checked){
                    playlistIDs.add(playlist.id)
                } else {
                    playlistIDs.remove(playlist.id)
                }
                viewModel.updatePlaylistIDs(playlistIDs)
            }
        )
    }
}