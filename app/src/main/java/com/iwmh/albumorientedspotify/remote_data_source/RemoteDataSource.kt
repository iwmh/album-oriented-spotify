package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.Episode
import com.iwmh.albumorientedspotify.repository.model.api.PagingObject
import com.iwmh.albumorientedspotify.repository.model.api.Playlist

interface RemoteDataSource {

    // Store data to local storage.
    fun storeData(keyString: String, valueString: String)

    // Read data from local storage.
    fun readData(keyString: String): String?

    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's playlists
    suspend fun getUsersPlaylists(url: String?): PagingObject<Playlist>

    // Get Show Episodes
    suspend fun getShowEpisodes(showId: String?,  url: String?): PagingObject<Episode>
}