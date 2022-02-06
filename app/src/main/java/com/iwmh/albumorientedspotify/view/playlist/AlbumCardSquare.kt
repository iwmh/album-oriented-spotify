package com.iwmh.albumorientedspotify.view.playlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.iwmh.albumorientedspotify.repository.model.api.Artist
import java.util.concurrent.TimeUnit

@Composable
fun AlbumCardSquare(
        albumID: String?,
        albumName: String?,
        imageUrl: String?,
        artists: List<Artist>,
        releaseDate: String,
        //onClick: () -> Unit
){
    Column(
        //modifier =  Modifier.padding(top = 10.dp).clickable ( onClick = onClick )
        modifier =  Modifier.padding(all = 10.dp)
    ) {
        val artistNameList = artists.map { it.name }
        Row {
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
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = artistNameList.joinToString(", "),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = releaseDate,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
        ) {
            Text(
                modifier = Modifier
                    .border(BorderStroke(2.dp, Color.Black))
                    .padding(all = 5.dp)
                    .fillMaxWidth(),
                text = "See all tracks",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .border(BorderStroke(0.5.dp, Color.Black)),
                text = "fav"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .border(BorderStroke(0.5.dp, Color.Black)),
                text = "tmp_new"
            )
        }
    }
}