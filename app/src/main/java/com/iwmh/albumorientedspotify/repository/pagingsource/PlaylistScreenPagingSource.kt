package com.iwmh.albumorientedspotify.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.model.api.TrackItem
import com.iwmh.albumorientedspotify.util.InjectableConstants

class PlaylistScreenPagingSource constructor(
    private val playlistID: String?,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
) : PagingSource<String, TrackItem>() {

    /**
     * Remember to call refreshTokensIfNecessary() before any api call.
     */

    override fun getRefreshKey(state: PagingState<String, TrackItem>): String? {
        var anchor = state.anchorPosition
        return injectableConstants.baseUrl + "playlists/" + playlistID + "/tracks";
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TrackItem> {
        return try {
            remoteDataSource.refreshTokensIfNecessary()
            val response = remoteDataSource.getPlaylistItems(playlistID, params.key)
            LoadResult.Page(
                data = response.items,
                nextKey = response.next,
                prevKey = response.previous
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}