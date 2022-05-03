package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.PagingObject
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
import com.iwmh.albumorientedspotify.repository.model.api.Profile
import com.iwmh.albumorientedspotify.repository.model.api.TrackItem
import kotlinx.coroutines.Deferred

interface RemoteDataSource {

    // Store data to local storage.
    fun storeData(keyString: String, valueString: String)

    // Read data from local storage.
    fun readData(keyString: String): String?

    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's playlists
    suspend fun getUsersPlaylists(url: String?): PagingObject<Playlist>

    // Get plsylist's item
    suspend fun getPlaylistItems(playlistID: String?,  url: String?): PagingObject<TrackItem>

    // Get Current User's Profile
    suspend fun getCurrentUsersProfile(): Profile

    // Get playlsit
    fun getPlaylistAsync(playlistID: String?): Deferred<Playlist>
}