package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.*
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiClient: WebApiClient,
    private val localStorage: LocalStorage
) : RemoteDataSource {

    // Store data.
    override fun storeData(keyString: String, valueString: String){
        localStorage.storeData(keyString, valueString)
    }

    // Read data.
    override fun readData(keyString: String): String? {
        return localStorage.readData(keyString)
    }

    override suspend fun refreshTokensIfNecessary(): String {
        return apiClient.refreshTokensIfNecessary()
    }

    override suspend fun getUsersPlaylists(initialUrl: String?): PagingObject<Playlist> {
        return apiClient.getUsersPlaylists(initialUrl)
    }

    // Get Playlist's items
    override suspend fun getPlaylistItems(playlistID: String?, url: String?): PagingObject<TrackItem> {
        return apiClient.getPlaylistItems(playlistID, url)
    }

    // Get Album's items
    override suspend fun getAlbumItems(albumID: String?, url: String?): PagingObject<Track> {
        return apiClient.getAlbumItems(albumID, url)
    }

    // Get Current User's Profile
    override suspend fun getCurrentUsersProfile(): Profile{
        return apiClient.getCurrentUsersProfile()
    }

    // Add items to playlist
    override suspend fun addItemsToPlaylist(playlistID: String, listOfSpotifyUris: List<String>): PostResponse{
        return apiClient.addItemsToPlaylist(playlistID, listOfSpotifyUris)
    }

    // Get playlsit
    override fun getPlaylistAsync(playlistID: String?): Deferred<Playlist>{
        return apiClient.getPlaylistAsync(playlistID)
    }

}