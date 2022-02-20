package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.PagingObject
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
import com.iwmh.albumorientedspotify.repository.model.api.Profile
import com.iwmh.albumorientedspotify.repository.model.api.TrackItem

interface WebApiClient {
    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's Playlists
    suspend fun getUsersPlaylists(url: String?): PagingObject<Playlist>

    // Get Playlist's items
    suspend fun getPlaylistItems(playlistID: String?,  url: String?): PagingObject<TrackItem>

    // Get Current User's Profile
    suspend fun getCurrentUsersProfile(): Profile
}