package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.Episode
import com.iwmh.albumorientedspotify.repository.model.api.PagingObject
import com.iwmh.albumorientedspotify.repository.model.api.Playlist

interface WebApiClient {
    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's Playlists
    suspend fun getUsersPlaylists(url: String?): PagingObject<Playlist>

    // Get Show Episodes
    suspend fun getShowEpisodes(showId: String?,  url: String?): PagingObject<Episode>
}