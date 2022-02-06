package com.iwmh.albumorientedspotify.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
import com.iwmh.albumorientedspotify.util.InjectableConstants

class LibraryScreenPagingSource constructor(
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
) : PagingSource<String, Playlist>() {

    /**
     * Remember to call refreshTokensIfNecessary() before any api call.
     */

    override fun getRefreshKey(state: PagingState<String, Playlist>): String? {
        var anchor = state.anchorPosition
        // the default URL (with no params about nextKey.)
        return injectableConstants.baseUrl + "/me/playlists"
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Playlist> {
        return try {
            remoteDataSource.refreshTokensIfNecessary()
            val response = remoteDataSource.getUsersPlaylists(params.key)
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