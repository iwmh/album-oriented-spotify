package com.iwmh.albumorientedspotify.view.episodes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.util.concurrent.TimeUnit

@Composable
fun EpisodeCardSquare(
        episodeName: String?,
        imageUrl: String?,
        description: String,
        duration: Int,
        releaseDate: String,
        onClick: () -> Unit
){
    Column(
        modifier =  Modifier.padding(top = 10.dp).clickable ( onClick = onClick )
    ) {
        Row {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "$episodeName.",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row {
            Text(
                text = description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row {
            Text("$releaseDate, ${TimeUnit.MILLISECONDS.toMinutes(duration.toLong())} min.")
        }
    }
}