package com.iwmh.albumorientedspotify.view.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AuthScreen(
    authIntent: Intent,
    launcher: ActivityResultLauncher<Intent>
){
    Button(
        onClick = {
            launcher.launch(authIntent)
        },
    ) {
        Text(text = "Log in with Spotify")
    }
}
