package com.iwmh.albumorientedspotify.view.home

import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iwmh.albumorientedspotify.Screen
import com.iwmh.albumorientedspotify.view.library.LibraryScreenViewModel

@Composable
fun HomeScreen(name: String) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
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
    }
}
