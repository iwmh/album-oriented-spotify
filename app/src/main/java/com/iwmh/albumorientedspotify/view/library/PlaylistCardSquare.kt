package com.iwmh.albumorientedspotify.view.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.iwmh.albumorientedspotify.repository.model.api.Playlist

@Composable
fun PlaylistCardSquare(playlist: Playlist?, onClick: () -> Unit){
    val padding = 8.dp
    Row(
        Modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(playlist!!.images[0].url),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.size(padding))
        Column(
            Modifier.align(Alignment.CenterVertically)
        ) {
            Text(text = playlist.name)
        }
    }
}