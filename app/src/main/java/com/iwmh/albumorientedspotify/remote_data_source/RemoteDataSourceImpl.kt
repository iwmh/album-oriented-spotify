package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.Episode
import com.iwmh.albumorientedspotify.repository.model.api.PagingObject
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
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

    // Get Show Episodes
    override suspend fun getShowEpisodes(showId: String?, url: String?): PagingObject<Episode> {
        return apiClient.getShowEpisodes(showId, url)
    }


}