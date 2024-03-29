package com.iwmh.albumorientedspotify.view.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iwmh.albumorientedspotify.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    if(uiState.value.userID.isBlank()){
        Toast.makeText(LocalContext.current, "Please wait until the button activated...", Toast.LENGTH_LONG).show()
    }

    Column() {
        Text(
            text = "Welcome, " + uiState.value.userID + "!",
            fontSize = 25.sp,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "This is Album-Oriented Spotify app.",
            fontSize = 20.sp
        )
        Text(text = "・You can browse contents of playlists which owned by someone.")
        Text(text = "・Inside playlists, you only see albums. Singles are excluded.")
        Text(text = "・You can add albums' all tracks to playlists you like by tapping one button (Add this buttons in the setting screen).")
        Spacer(modifier = Modifier.padding(6.dp))
        Button(
            enabled = uiState.value.userID.isNotBlank(),
            onClick = {
                // Navigate to Settings screen.
                navController.navigate("${Screen.Settings.route}")
            })
        {
            Icon(
                Icons.Filled.Settings,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSize))
            Text("Open Settings")
        }
    }
}
