package com.iwmh.albumorientedspotify.view.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.iwmh.albumorientedspotify.repository.model.api.Show

@Composable
fun ShowCardSquare(show: Show?, onClick: () -> Unit){
    Column(
        Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(show!!.images[1].url),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Text(text = show!!.name)
    }
}