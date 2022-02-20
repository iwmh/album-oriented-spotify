package com.iwmh.albumorientedspotify.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iwmh.albumorientedspotify.Screen
import com.iwmh.albumorientedspotify.view.library.LibraryScreenViewModel

@Composable
fun HomeScreen(name: String) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
    Column() {
        Text(text = " a list of Spotify podcast information .....")
        Text(text = "Welcome, " + uiState.value.userID + "!")
    }
}
