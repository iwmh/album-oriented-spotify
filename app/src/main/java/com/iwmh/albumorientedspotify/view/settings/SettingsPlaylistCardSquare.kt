package com.iwmh.albumorientedspotify.view.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
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