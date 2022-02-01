package com.iwmh.albumorientedspotify.view.episodedetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EpisodeDetailScreen(
    episodeName: String,
    imageUrl: String,
    description: String,
    duration: Int,
    releaseDate: String
) {
    val viewModel: EpisodeDetailScreenViewModel = hiltViewModel()

    EpisodeDetailCardSquare(
        episodeName = episodeName,
        imageUrl = imageUrl,
        description = description,
        duration = duration,
        releaseDate = releaseDate
    )


}
