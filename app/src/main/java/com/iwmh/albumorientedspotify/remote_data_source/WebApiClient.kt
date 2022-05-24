package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.*
import kotlinx.coroutines.Deferred

interface WebApiClient {
    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's Playlists
    suspend fun getUsersPlaylists(url: String?): PagingObject<Playlist>

    // Get Playlist's items
    suspend fun getPlaylistItems(playlistID: String?,  url: String?): PagingObject<TrackItem>

    // Get Album's items
    suspend fun getAlbumItems(albumID: String?,  url: String?): PagingObject<Track>

    // Get Current User's Profile
    suspend fun getCurrentUsersProfile(): Profile

    // Add items to playlist
    suspend fun addItemsToPlaylist(playlistID: String, listOfSpotifyUris: List<String>): PostResponse

    // Get Playlist
    fun getPlaylistAsync(playlistID: String?): Deferred<Playlist>
}