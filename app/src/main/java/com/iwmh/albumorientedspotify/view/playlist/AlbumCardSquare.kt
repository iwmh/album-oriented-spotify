package com.iwmh.albumorientedspotify.view.playlist

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.iwmh.albumorientedspotify.repository.model.api.Playlist

@Composable
fun AlbumCardSquare(
        context: Context,
        albumID: String?,
        albumName: String?,
        totalTracksInAlbum: Int?,
        imageUrl: String?,
        artists: List<String>?,
        releaseDate: String?,
        playlistIDList: List<String>,
        playlistIDAndNameList: List<Playlist>,
){
    Column(
        //modifier =  Modifier.padding(top = 10.dp).clickable ( onClick = onClick )
        modifier =  Modifier.padding(all = 10.dp)
    ) {
        Row(
            modifier = Modifier.height(100.dp)
        ) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text(
                    text = albumName!!,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp, start = 2.dp),
                    overflow = TextOverflow.Ellipsis
                )
                if (artists != null) {
                    Text(
                        text = artists.joinToString(", "),
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(x = 5.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = releaseDate ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = 5.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = " $totalTracksInAlbum tracks.",
            )
        }
        Column{
            for (playlistID in playlistIDList) {
                YourTargetPlaylistCard(
                    context = context,
                    albumID = albumID,
                    playlistID = playlistID,
                    playlistName = playlistIDAndNameList.find {
                        it.id == playlistID
                    }?.name,
                )
            }
        }
    }
}

@Composable
fun YourTargetPlaylistCard(
    context: Context,
    albumID: String?,
    playlistID: String,
    playlistName: String?,
){
    val viewModel: PlaylistScreenViewModel = hiltViewModel()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .border(BorderStroke(0.5.dp, Color.Black)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = playlistName ?: "",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Start,
        )
        Button(
            onClick = {
                viewModel.addAllTracksInAlbumToPlaylist(albumID!!, playlistID, playlistName!!)
                },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = "Add all tracks",
                fontSize = 14.sp,
            )
        }
    }
}

/*
@Preview
@Composable
fun PreviewAlbumCard(){
   AlbumCardSquare(
       albumID = "",
       albumName = "Landmark",
       totalTracksInAlbum = 12,
       imageUrl = "https://i.scdn.co/image/ab67616d00001e02d8041a531487d0e0e4cfb41f",
       artists = listOf("a", "b"),
       releaseDate = "2022-12-12",
       playlistIDList = emptyList(),
       playlistIDAndNameList = emptyList()
   )
}
 */
