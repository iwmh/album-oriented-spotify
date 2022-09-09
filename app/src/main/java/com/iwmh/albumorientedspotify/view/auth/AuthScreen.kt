package com.iwmh.albumorientedspotify.view.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AuthScreen(
    authIntent: Intent,
    launcher: ActivityResultLauncher<Intent>
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {
                launcher.launch(authIntent)
            },
        ) {
            Text(text = "Log In To Spotify To Get Started")
        }
    }
}
