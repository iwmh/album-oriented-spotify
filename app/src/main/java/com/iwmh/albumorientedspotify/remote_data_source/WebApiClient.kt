package com.iwmh.albumorientedspotify.remote_data_source

import com.iwmh.albumorientedspotify.repository.model.api.Episode
import com.iwmh.albumorientedspotify.repository.model.api.ItemShow
import com.iwmh.albumorientedspotify.repository.model.api.PagingObject

interface WebApiClient {
    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

    // Get User's Saved Shows
    suspend fun getUsersSavedShows(url: String?): PagingObject<ItemShow>

    // Get Show Episodes
    suspend fun getShowEpisodes(showId: String?,  url: String?): PagingObject<Episode>
}